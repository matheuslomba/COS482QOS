import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IHd } from '@/shared/model/hd.model';

import HdService from './hd.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Hd extends Vue {
  @Inject('hdService') private hdService: () => HdService;
  private removeId: number = null;

  public hds: IHd[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllHds();
  }

  public clear(): void {
    this.retrieveAllHds();
  }

  public retrieveAllHds(): void {
    this.isFetching = true;

    this.hdService()
      .retrieve()
      .then(
        res => {
          this.hds = res.data;
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

  public prepareRemove(instance: IHd): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeHd(): void {
    this.hdService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cabonpontocomApp.hd.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllHds();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
