import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskProceedCheckoutService from './task-proceed-checkout.service';
import { TaskProceedCheckoutContext } from './task-proceed-checkout.model';

const validations: any = {
  taskContext: {
    clientOrderProcess: {
      clientOrder: {
        orderID: {},
        orderDate: {},
        clientName: {},
        numComponents: {},
        deliveryAdd: {},
        proceedToCheckout: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskProceedCheckoutExecuteComponent extends Vue {
  private taskProceedCheckoutService: TaskProceedCheckoutService = new TaskProceedCheckoutService();
  private taskContext: TaskProceedCheckoutContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskProceedCheckoutService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskProceedCheckoutService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
