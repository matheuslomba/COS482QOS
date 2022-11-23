import { Component, Vue, Inject } from 'vue-property-decorator';

import MotherBoardService from '@/entities/mother-board/mother-board.service';
import { IMotherBoard } from '@/shared/model/mother-board.model';

import CpuService from '@/entities/cpu/cpu.service';
import { ICpu } from '@/shared/model/cpu.model';

import GpuService from '@/entities/gpu/gpu.service';
import { IGpu } from '@/shared/model/gpu.model';

import RamService from '@/entities/ram/ram.service';
import { IRam } from '@/shared/model/ram.model';

import HdService from '@/entities/hd/hd.service';
import { IHd } from '@/shared/model/hd.model';

import PowerSourceService from '@/entities/power-source/power-source.service';
import { IPowerSource } from '@/shared/model/power-source.model';

import TaskChooseComponentsService from './task-choose-components.service';
import { TaskChooseComponentsContext } from './task-choose-components.model';

const validations: any = {
  taskContext: {
    clientOrderProcess: {
      clientOrder: {
        orderID: {},
        orderDate: {},
        clientName: {},
        numComponents: {},
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

  @Inject('motherBoardService') private motherBoardService: () => MotherBoardService;

  public motherBoards: IMotherBoard[] = [];

  @Inject('cpuService') private cpuService: () => CpuService;

  public cpus: ICpu[] = [];

  @Inject('gpuService') private gpuService: () => GpuService;

  public gpus: IGpu[] = [];

  @Inject('ramService') private ramService: () => RamService;

  public rams: IRam[] = [];

  @Inject('hdService') private hdService: () => HdService;

  public hds: IHd[] = [];

  @Inject('powerSourceService') private powerSourceService: () => PowerSourceService;

  public powerSources: IPowerSource[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
      vm.initRelationships();
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

  public initRelationships(): void {
    this.motherBoardService()
      .retrieve()
      .then(res => {
        this.motherBoards = res.data;
      });
    this.cpuService()
      .retrieve()
      .then(res => {
        this.cpus = res.data;
      });
    this.gpuService()
      .retrieve()
      .then(res => {
        this.gpus = res.data;
      });
    this.ramService()
      .retrieve()
      .then(res => {
        this.rams = res.data;
      });
    this.hdService()
      .retrieve()
      .then(res => {
        this.hds = res.data;
      });
    this.powerSourceService()
      .retrieve()
      .then(res => {
        this.powerSources = res.data;
      });
  }
}
