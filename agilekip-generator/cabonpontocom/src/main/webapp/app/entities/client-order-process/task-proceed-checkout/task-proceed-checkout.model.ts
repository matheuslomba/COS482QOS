import { IClientOrderProcess } from '@/shared/model/client-order-process.model';

export class TaskProceedCheckoutContext {
  taskInstance?: any = {};
  clientOrderProcess?: IClientOrderProcess = {};
}
