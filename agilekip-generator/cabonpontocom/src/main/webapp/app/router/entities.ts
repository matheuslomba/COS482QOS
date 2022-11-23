import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const ClientOrder = () => import('@/entities/client-order/client-order.vue');
// prettier-ignore
const ClientOrderDetails = () => import('@/entities/client-order/client-order-details.vue');
// prettier-ignore
const ClientOrderProcessDetails = () => import('@/entities/client-order-process/client-order-process-details.vue');
// prettier-ignore
const ClientOrderProcessList = () => import('@/entities/client-order-process/client-order-process-list.vue');
// prettier-ignore
const ClientOrderStartFormInit = () => import('@/entities/client-order-process/client-order-start-form-init.vue');
// prettier-ignore
const ClientOrderProcess_TaskChooseComponentsDetails = () => import('@/entities/client-order-process/task-choose-components/task-choose-components-details.vue');
// prettier-ignore
const ClientOrderProcess_TaskChooseComponentsExecute = () => import('@/entities/client-order-process/task-choose-components/task-choose-components-execute.vue');
// prettier-ignore
const ClientOrderProcess_TaskOfferAssemblyDetails = () => import('@/entities/client-order-process/task-offer-assembly/task-offer-assembly-details.vue');
// prettier-ignore
const ClientOrderProcess_TaskOfferAssemblyExecute = () => import('@/entities/client-order-process/task-offer-assembly/task-offer-assembly-execute.vue');
// prettier-ignore
const ClientOrderProcess_TaskProceedCheckoutDetails = () => import('@/entities/client-order-process/task-proceed-checkout/task-proceed-checkout-details.vue');
// prettier-ignore
const ClientOrderProcess_TaskProceedCheckoutExecute = () => import('@/entities/client-order-process/task-proceed-checkout/task-proceed-checkout-execute.vue');
// prettier-ignore
const ClientOrderProcess_TaskGetPaidDetails = () => import('@/entities/client-order-process/task-get-paid/task-get-paid-details.vue');
// prettier-ignore
const ClientOrderProcess_TaskGetPaidExecute = () => import('@/entities/client-order-process/task-get-paid/task-get-paid-execute.vue');
// prettier-ignore
const Cpu = () => import('@/entities/cpu/cpu.vue');
// prettier-ignore
const CpuUpdate = () => import('@/entities/cpu/cpu-update.vue');
// prettier-ignore
const CpuDetails = () => import('@/entities/cpu/cpu-details.vue');
// prettier-ignore
const Gpu = () => import('@/entities/gpu/gpu.vue');
// prettier-ignore
const GpuUpdate = () => import('@/entities/gpu/gpu-update.vue');
// prettier-ignore
const GpuDetails = () => import('@/entities/gpu/gpu-details.vue');
// prettier-ignore
const Hd = () => import('@/entities/hd/hd.vue');
// prettier-ignore
const HdUpdate = () => import('@/entities/hd/hd-update.vue');
// prettier-ignore
const HdDetails = () => import('@/entities/hd/hd-details.vue');
// prettier-ignore
const MotherBoard = () => import('@/entities/mother-board/mother-board.vue');
// prettier-ignore
const MotherBoardUpdate = () => import('@/entities/mother-board/mother-board-update.vue');
// prettier-ignore
const MotherBoardDetails = () => import('@/entities/mother-board/mother-board-details.vue');
// prettier-ignore
const PowerSource = () => import('@/entities/power-source/power-source.vue');
// prettier-ignore
const PowerSourceUpdate = () => import('@/entities/power-source/power-source-update.vue');
// prettier-ignore
const PowerSourceDetails = () => import('@/entities/power-source/power-source-details.vue');
// prettier-ignore
const Ram = () => import('@/entities/ram/ram.vue');
// prettier-ignore
const RamUpdate = () => import('@/entities/ram/ram-update.vue');
// prettier-ignore
const RamDetails = () => import('@/entities/ram/ram-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/client-order',
    name: 'ClientOrder',
    component: ClientOrder,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/client-order/:clientOrderId/view',
    name: 'ClientOrderView',
    component: ClientOrderDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/instance/:processInstanceId/view',
    name: 'ClientOrderProcessView',
    component: ClientOrderProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/instances',
    name: 'ClientOrderProcessList',
    component: ClientOrderProcessList,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/init',
    name: 'ClientOrderStartFormInit',
    component: ClientOrderStartFormInit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskChooseComponents/:taskInstanceId/view',
    name: 'ClientOrderProcess_TaskChooseComponentsDetails',
    component: ClientOrderProcess_TaskChooseComponentsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskChooseComponents/:taskInstanceId/execute',
    name: 'ClientOrderProcess_TaskChooseComponentsExecute',
    component: ClientOrderProcess_TaskChooseComponentsExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskOfferAssembly/:taskInstanceId/view',
    name: 'ClientOrderProcess_TaskOfferAssemblyDetails',
    component: ClientOrderProcess_TaskOfferAssemblyDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskOfferAssembly/:taskInstanceId/execute',
    name: 'ClientOrderProcess_TaskOfferAssemblyExecute',
    component: ClientOrderProcess_TaskOfferAssemblyExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskProceedCheckout/:taskInstanceId/view',
    name: 'ClientOrderProcess_TaskProceedCheckoutDetails',
    component: ClientOrderProcess_TaskProceedCheckoutDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskProceedCheckout/:taskInstanceId/execute',
    name: 'ClientOrderProcess_TaskProceedCheckoutExecute',
    component: ClientOrderProcess_TaskProceedCheckoutExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskGetPaid/:taskInstanceId/view',
    name: 'ClientOrderProcess_TaskGetPaidDetails',
    component: ClientOrderProcess_TaskGetPaidDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/ClientOrderProcess/task/TaskGetPaid/:taskInstanceId/execute',
    name: 'ClientOrderProcess_TaskGetPaidExecute',
    component: ClientOrderProcess_TaskGetPaidExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cpu',
    name: 'Cpu',
    component: Cpu,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cpu/new',
    name: 'CpuCreate',
    component: CpuUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cpu/:cpuId/edit',
    name: 'CpuEdit',
    component: CpuUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cpu/:cpuId/view',
    name: 'CpuView',
    component: CpuDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/gpu',
    name: 'Gpu',
    component: Gpu,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/gpu/new',
    name: 'GpuCreate',
    component: GpuUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/gpu/:gpuId/edit',
    name: 'GpuEdit',
    component: GpuUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/gpu/:gpuId/view',
    name: 'GpuView',
    component: GpuDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hd',
    name: 'Hd',
    component: Hd,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hd/new',
    name: 'HdCreate',
    component: HdUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hd/:hdId/edit',
    name: 'HdEdit',
    component: HdUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hd/:hdId/view',
    name: 'HdView',
    component: HdDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/mother-board',
    name: 'MotherBoard',
    component: MotherBoard,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/mother-board/new',
    name: 'MotherBoardCreate',
    component: MotherBoardUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/mother-board/:motherBoardId/edit',
    name: 'MotherBoardEdit',
    component: MotherBoardUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/mother-board/:motherBoardId/view',
    name: 'MotherBoardView',
    component: MotherBoardDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-source',
    name: 'PowerSource',
    component: PowerSource,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-source/new',
    name: 'PowerSourceCreate',
    component: PowerSourceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-source/:powerSourceId/edit',
    name: 'PowerSourceEdit',
    component: PowerSourceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-source/:powerSourceId/view',
    name: 'PowerSourceView',
    component: PowerSourceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ram',
    name: 'Ram',
    component: Ram,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ram/new',
    name: 'RamCreate',
    component: RamUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ram/:ramId/edit',
    name: 'RamEdit',
    component: RamUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ram/:ramId/view',
    name: 'RamView',
    component: RamDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
