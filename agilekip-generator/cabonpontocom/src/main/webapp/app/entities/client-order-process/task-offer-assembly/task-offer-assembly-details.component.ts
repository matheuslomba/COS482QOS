import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskOfferAssemblyService from './task-offer-assembly.service';
import { TaskOfferAssemblyContext } from './task-offer-assembly.model';

@Component
export default class TaskOfferAssemblyDetailsComponent extends Vue {
  private taskOfferAssemblyService: TaskOfferAssemblyService = new TaskOfferAssemblyService();
  private taskContext: TaskOfferAssemblyContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskOfferAssemblyService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
