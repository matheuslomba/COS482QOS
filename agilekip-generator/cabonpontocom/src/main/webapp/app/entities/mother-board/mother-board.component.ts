import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMotherBoard } from '@/shared/model/mother-board.model';

import MotherBoardService from './mother-board.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MotherBoard extends Vue {
  @Inject('motherBoardService') private motherBoardService: () => MotherBoardService;
  private removeId: number = null;

  public motherBoards: IMotherBoard[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMotherBoards();
  }

  public clear(): void {
    this.retrieveAllMotherBoards();
  }

  public retrieveAllMotherBoards(): void {
    this.isFetching = true;

    this.motherBoardService()
      .retrieve()
      .then(
        res => {
          this.motherBoards = res.data;
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

  public prepareRemove(instance: IMotherBoard): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMotherBoard(): void {
    this.motherBoardService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cabonpontocomApp.motherBoard.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMotherBoards();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
