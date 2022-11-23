import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICpu } from '@/shared/model/cpu.model';
import CpuService from './cpu.service';

@Component
export default class CpuDetails extends Vue {
  @Inject('cpuService') private cpuService: () => CpuService;
  public cpu: ICpu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cpuId) {
        vm.retrieveCpu(to.params.cpuId);
      }
    });
  }

  public retrieveCpu(cpuId) {
    this.cpuService()
      .find(cpuId)
      .then(res => {
        this.cpu = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
