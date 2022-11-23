import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGpu } from '@/shared/model/gpu.model';

import GpuService from './gpu.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Gpu extends Vue {
  @Inject('gpuService') private gpuService: () => GpuService;
  private removeId: number = null;

  public gpus: IGpu[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGpus();
  }

  public clear(): void {
    this.retrieveAllGpus();
  }

  public retrieveAllGpus(): void {
    this.isFetching = true;

    this.gpuService()
      .retrieve()
      .then(
        res => {
          this.gpus = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IGpu): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGpu(): void {
    this.gpuService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cabonpontocomApp.gpu.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllGpus();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
