import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRam } from '@/shared/model/ram.model';
import RamService from './ram.service';

@Component
export default class RamDetails extends Vue {
  @Inject('ramService') private ramService: () => RamService;
  public ram: IRam = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ramId) {
        vm.retrieveRam(to.params.ramId);
      }
    });
  }

  public retrieveRam(ramId) {
    this.ramService()
      .find(ramId)
      .then(res => {
        this.ram = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
