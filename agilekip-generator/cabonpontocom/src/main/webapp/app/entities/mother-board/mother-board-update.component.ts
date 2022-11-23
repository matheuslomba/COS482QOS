import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMotherBoard, MotherBoard } from '@/shared/model/mother-board.model';
import MotherBoardService from './mother-board.service';

const validations: any = {
  motherBoard: {
    motherBoardName: {},
    motherBoardPrice: {},
  },
};

@Component({
  validations,
})
export default class MotherBoardUpdate extends Vue {
  @Inject('motherBoardService') private motherBoardService: () => MotherBoardService;
  public motherBoard: IMotherBoard = new MotherBoard();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.motherBoardId) {
        vm.retrieveMotherBoard(to.params.motherBoardId);
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
    if (this.motherBoard.id) {
      this.motherBoardService()
        .update(this.motherBoard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.motherBoard.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.motherBoardService()
        .create(this.motherBoard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cabonpontocomApp.motherBoard.created', { param: param.id });
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

  public retrieveMotherBoard(motherBoardId): void {
    this.motherBoardService()
      .find(motherBoardId)
      .then(res => {
        this.motherBoard = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
