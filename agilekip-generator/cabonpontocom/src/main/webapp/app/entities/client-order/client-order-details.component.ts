import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClientOrder } from '@/shared/model/client-order.model';
import ClientOrderService from './client-order.service';

@Component
export default class ClientOrderDetails extends Vue {
  @Inject('clientOrderService') private clientOrderService: () => ClientOrderService;
  public clientOrder: IClientOrder = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientOrderId) {
        vm.retrieveClientOrder(to.params.clientOrderId);
      }
    });
  }

  public retrieveClientOrder(clientOrderId) {
    this.clientOrderService()
      .find(clientOrderId)
      .then(res => {
        this.clientOrder = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
