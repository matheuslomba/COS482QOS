import { Component, Vue, Inject } from 'vue-property-decorator';
import { IClientOrderProcess } from '@/shared/model/client-order-process.model';

import { ProcessDefinition, ProcessDefinitionService } from 'akip-vue-community';

import ClientOrderProcessService from './client-order-process.service';

@Component
export default class ClientOrderProcessListComponent extends Vue {
  @Inject('clientOrderProcessService') private clientOrderProcessService: () => ClientOrderProcessService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'ClientOrderProcess';
  public processDefinition: ProcessDefinition = new ProcessDefinition();
  public clientOrderProcessList: IClientOrderProcess[] = [];

  public isFetchingProcessDefinition = false;
  public isFetchingProcessInstances = false;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.retrieveProcessDefinition();
    this.retrieveProcessInstances();
  }

  public retrieveProcessDefinition() {
    this.isFetchingProcessDefinition = true;
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(
      res => {
        this.processDefinition = res;
        this.isFetchingProcessDefinition = false;
      },
      err => {
        this.isFetchingProcessDefinition = false;
      }
    );
  }

  public retrieveProcessInstances(): void {
    this.isFetchingProcessInstances = true;
    this.clientOrderProcessService()
      .retrieve()
      .then(
        res => {
          this.clientOrderProcessList = res.data;
          this.isFetchingProcessInstances = false;
        },
        err => {
          this.isFetchingProcessInstances = false;
        }
      );
  }

  get isFetching(): boolean {
    return this.isFetchingProcessDefinition && this.isFetchingProcessInstances;
  }

  public handleSyncList(): void {
    this.retrieveProcessInstances();
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
