import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskGetPaidService from './task-get-paid.service';
import { TaskGetPaidContext } from './task-get-paid.model';

@Component
export default class TaskGetPaidDetailsComponent extends Vue {
  private taskGetPaidService: TaskGetPaidService = new TaskGetPaidService();
  private taskContext: TaskGetPaidContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskGetPaidService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
