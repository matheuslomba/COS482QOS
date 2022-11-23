import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskProceedCheckoutService from './task-proceed-checkout.service';
import { TaskProceedCheckoutContext } from './task-proceed-checkout.model';

@Component
export default class TaskProceedCheckoutDetailsComponent extends Vue {
  private taskProceedCheckoutService: TaskProceedCheckoutService = new TaskProceedCheckoutService();
  private taskContext: TaskProceedCheckoutContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskProceedCheckoutService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
