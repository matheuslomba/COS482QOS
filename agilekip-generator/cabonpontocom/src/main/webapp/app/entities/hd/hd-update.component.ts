import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHd, Hd } from '@/shared/model/hd.model';
import HdService from './hd.service';

const validations: any = {
  hd: {
    hdName: {},
    hdPrice: {},
  },
};

@Component({
  validations,
})
export default class HdUpdate extends Vue {
  @Inject('hdService') private hdService: () => HdService;
  public hd: IHd = new Hd();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hdId) {
        vm.retrieveHd(to.params.hdId);
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
    if (this.hd.id) {
      this.hdService()
        .update(this.hd)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.hd.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.hdService()
        .create(this.hd)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.hd.created', { param: param.id });
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

  public retrieveHd(hdId): void {
    this.hdService()
      .find(hdId)
      .then(res => {
        this.hd = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
