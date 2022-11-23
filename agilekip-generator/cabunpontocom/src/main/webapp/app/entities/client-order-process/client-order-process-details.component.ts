import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClientOrderProcess } from '@/shared/model/client-order-process.model';
import ClientOrderProcessService from './client-order-process.service';

@Component
export default class ClientOrderProcessDetailsComponent extends Vue {
  @Inject('clientOrderProcessService') private clientOrderProcessService: () => ClientOrderProcessService;
  public clientOrderProcess: IClientOrderProcess = {};

  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processInstanceId) {
        vm.retrieveClientOrderProcess(to.params.processInstanceId);
      }
    });
  }

  public retrieveClientOrderProcess(clientOrderProcessId) {
    this.isFetching = true;
    this.clientOrderProcessService()
      .find(clientOrderProcessId)
      .then(
        res => {
          this.clientOrderProcess = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public previousState() {
    this.$router.go(-1);
  }
}
