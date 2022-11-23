import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMotherBoard } from '@/shared/model/mother-board.model';
import MotherBoardService from './mother-board.service';

@Component
export default class MotherBoardDetails extends Vue {
  @Inject('motherBoardService') private motherBoardService: () => MotherBoardService;
  public motherBoard: IMotherBoard = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.motherBoardId) {
        vm.retrieveMotherBoard(to.params.motherBoardId);
      }
    });
  }

  public retrieveMotherBoard(motherBoardId) {
    this.motherBoardService()
      .find(motherBoardId)
      .then(res => {
        this.motherBoard = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
