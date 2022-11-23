import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHd } from '@/shared/model/hd.model';
import HdService from './hd.service';

@Component
export default class HdDetails extends Vue {
  @Inject('hdService') private hdService: () => HdService;
  public hd: IHd = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hdId) {
        vm.retrieveHd(to.params.hdId);
      }
    });
  }

  public retrieveHd(hdId) {
    this.hdService()
      .find(hdId)
      .then(res => {
        this.hd = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
