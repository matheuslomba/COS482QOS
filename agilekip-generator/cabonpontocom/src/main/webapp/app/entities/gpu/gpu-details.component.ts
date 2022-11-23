import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGpu } from '@/shared/model/gpu.model';
import GpuService from './gpu.service';

@Component
export default class GpuDetails extends Vue {
  @Inject('gpuService') private gpuService: () => GpuService;
  public gpu: IGpu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gpuId) {
        vm.retrieveGpu(to.params.gpuId);
      }
    });
  }

  public retrieveGpu(gpuId) {
    this.gpuService()
      .find(gpuId)
      .then(res => {
        this.gpu = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
