import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPowerSource } from '@/shared/model/power-source.model';

import PowerSourceService from './power-source.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PowerSource extends Vue {
  @Inject('powerSourceService') private powerSourceService: () => PowerSourceService;
  private removeId: number = null;

  public powerSources: IPowerSource[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPowerSources();
  }

  public clear(): void {
    this.retrieveAllPowerSources();
  }

  public retrieveAllPowerSources(): void {
    this.isFetching = true;

    this.powerSourceService()
      .retrieve()
      .then(
        res => {
          this.powerSources = res.data;
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

  public prepareRemove(instance: IPowerSource): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePowerSource(): void {
    this.powerSourceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cabonpontocomApp.powerSource.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPowerSources();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
