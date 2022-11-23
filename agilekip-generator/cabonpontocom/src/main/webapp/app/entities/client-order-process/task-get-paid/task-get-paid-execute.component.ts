import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskGetPaidService from './task-get-paid.service';
import { TaskGetPaidContext } from './task-get-paid.model';

const validations: any = {
  taskContext: {
    clientOrderProcess: {
      clientOrder: {
        orderID: {},
        orderDate: {},
        clientName: {},
        deliveryAdd: {},
        orderPrice: {},
        payment: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskGetPaidExecuteComponent extends Vue {
  private taskGetPaidService: TaskGetPaidService = new TaskGetPaidService();
  private taskContext: TaskGetPaidContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskGetPaidService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskGetPaidService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
