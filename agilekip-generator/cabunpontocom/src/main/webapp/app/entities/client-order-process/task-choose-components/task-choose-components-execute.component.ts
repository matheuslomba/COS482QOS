import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskChooseComponentsService from './task-choose-components.service';
import { TaskChooseComponentsContext } from './task-choose-components.model';

const validations: any = {
  taskContext: {
    clientOrderProcess: {
      clientOrder: {
        orderID: {},
        orderDate: {},
        clientName: {},
        orderComponents: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskChooseComponentsExecuteComponent extends Vue {
  private taskChooseComponentsService: TaskChooseComponentsService = new TaskChooseComponentsService();
  private taskContext: TaskChooseComponentsContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskChooseComponentsService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskChooseComponentsService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
