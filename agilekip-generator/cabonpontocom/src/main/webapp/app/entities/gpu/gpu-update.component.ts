import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGpu, Gpu } from '@/shared/model/gpu.model';
import GpuService from './gpu.service';

const validations: any = {
  gpu: {
    gpuName: {},
    gpuPrice: {},
  },
};

@Component({
  validations,
})
export default class GpuUpdate extends Vue {
  @Inject('gpuService') private gpuService: () => GpuService;
  public gpu: IGpu = new Gpu();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gpuId) {
        vm.retrieveGpu(to.params.gpuId);
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
    if (this.gpu.id) {
      this.gpuService()
        .update(this.gpu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.gpu.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.gpuService()
        .create(this.gpu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.gpu.created', { param: param.id });
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

  public retrieveGpu(gpuId): void {
    this.gpuService()
      .find(gpuId)
      .then(res => {
        this.gpu = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
