import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICpu } from '@/shared/model/cpu.model';

import CpuService from './cpu.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Cpu extends Vue {
  @Inject('cpuService') private cpuService: () => CpuService;
  private removeId: number = null;

  public cpus: ICpu[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCpus();
  }

  public clear(): void {
    this.retrieveAllCpus();
  }

  public retrieveAllCpus(): void {
    this.isFetching = true;

    this.cpuService()
      .retrieve()
      .then(
        res => {
          this.cpus = res.data;
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

  public prepareRemove(instance: ICpu): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCpu(): void {
    this.cpuService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cabonpontocomApp.cpu.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCpus();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
