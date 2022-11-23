import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskOfferAssemblyService from './task-offer-assembly.service';
import { TaskOfferAssemblyContext } from './task-offer-assembly.model';

const validations: any = {
  taskContext: {
    clientOrderProcess: {
      clientOrder: {
        orderID: {},
        orderDate: {},
        clientName: {},
        numComponents: {},
        assemblyPC: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskOfferAssemblyExecuteComponent extends Vue {
  private taskOfferAssemblyService: TaskOfferAssemblyService = new TaskOfferAssemblyService();
  private taskContext: TaskOfferAssemblyContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskOfferAssemblyService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskOfferAssemblyService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
