import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRam, Ram } from '@/shared/model/ram.model';
import RamService from './ram.service';

const validations: any = {
  ram: {
    ramName: {},
    ramPrice: {},
  },
};

@Component({
  validations,
})
export default class RamUpdate extends Vue {
  @Inject('ramService') private ramService: () => RamService;
  public ram: IRam = new Ram();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ramId) {
        vm.retrieveRam(to.params.ramId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.ram.id) {
      this.ramService()
        .update(this.ram)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.ram.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.ramService()
        .create(this.ram)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.ram.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveRam(ramId): void {
    this.ramService()
      .find(ramId)
      .then(res => {
        this.ram = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
