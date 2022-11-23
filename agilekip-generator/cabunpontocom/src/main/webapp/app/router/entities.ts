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
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
