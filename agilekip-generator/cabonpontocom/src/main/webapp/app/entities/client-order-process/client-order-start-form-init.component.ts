import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClientOrderProcess, ClientOrderProcess } from '@/shared/model/client-order-process.model';

import { ProcessInstance, ProcessDefinitionService } from 'akip-vue-community';

import { ClientOrder } from '@/shared/model/client-order.model';
import ClientOrderProcessService from './client-order-process.service';

const validations: any = {
  clientOrderProcess: {
    clientOrder: {
      orderID: {},
      orderDate: {},
      clientName: {},
      clientEmail: {},
    },
  },
};

@Component({
  validations,
})
export default class ClientOrderStartFormInitComponent extends Vue {
  @Inject('clientOrderProcessService') private clientOrderProcessService: () => ClientOrderProcessService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'ClientOrderProcess';
  public clientOrderProcess: IClientOrderProcess = new ClientOrderProcess();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initClientOrderStartForm();
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;

    this.clientOrderProcessService()
      .create(this.clientOrderProcess)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = this.$t('cabonpontocomApp.clientOrderStartForm.created', { param: param.id });
        this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Success',
          variant: 'success',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public initClientOrderStartForm(): void {
    this.clientOrderProcess.clientOrder = new ClientOrder();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(res => {
      this.clientOrderProcess.processInstance = new ProcessInstance();
      this.clientOrderProcess.processInstance.processDefinition = res;
    });
  }
}
