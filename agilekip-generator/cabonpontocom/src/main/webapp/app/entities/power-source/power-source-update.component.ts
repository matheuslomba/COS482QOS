import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPowerSource, PowerSource } from '@/shared/model/power-source.model';
import PowerSourceService from './power-source.service';

const validations: any = {
  powerSource: {
    powerSourceName: {},
    powerSourcePrice: {},
  },
};

@Component({
  validations,
})
export default class PowerSourceUpdate extends Vue {
  @Inject('powerSourceService') private powerSourceService: () => PowerSourceService;
  public powerSource: IPowerSource = new PowerSource();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.powerSourceId) {
        vm.retrievePowerSource(to.params.powerSourceId);
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
    if (this.powerSource.id) {
      this.powerSourceService()
        .update(this.powerSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.powerSource.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.powerSourceService()
        .create(this.powerSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.powerSource.created', { param: param.id });
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

  public retrievePowerSource(powerSourceId): void {
    this.powerSourceService()
      .find(powerSourceId)
      .then(res => {
        this.powerSource = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
