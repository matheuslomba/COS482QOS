import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskChooseComponentsService from './task-choose-components.service';
import { TaskChooseComponentsContext } from './task-choose-components.model';

@Component
export default class TaskChooseComponentsDetailsComponent extends Vue {
  private taskChooseComponentsService: TaskChooseComponentsService = new TaskChooseComponentsService();
  private taskContext: TaskChooseComponentsContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskChooseComponentsService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
