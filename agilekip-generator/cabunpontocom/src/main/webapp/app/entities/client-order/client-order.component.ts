import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IClientOrder } from '@/shared/model/client-order.model';

import ClientOrderService from './client-order.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ClientOrder extends Vue {
  @Inject('clientOrderService') private clientOrderService: () => ClientOrderService;
  private removeId: number = null;

  public clientOrders: IClientOrder[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllClientOrders();
  }

  public clear(): void {
    this.retrieveAllClientOrders();
  }

  public retrieveAllClientOrders(): void {
    this.isFetching = true;

    this.clientOrderService()
      .retrieve()
      .then(
        res => {
          this.clientOrders = res.data;
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

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
