import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPowerSource } from '@/shared/model/power-source.model';
import PowerSourceService from './power-source.service';

@Component
export default class PowerSourceDetails extends Vue {
  @Inject('powerSourceService') private powerSourceService: () => PowerSourceService;
  public powerSource: IPowerSource = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.powerSourceId) {
        vm.retrievePowerSource(to.params.powerSourceId);
      }
    });
  }

  public retrievePowerSource(powerSourceId) {
    this.powerSourceService()
      .find(powerSourceId)
      .then(res => {
        this.powerSource = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
