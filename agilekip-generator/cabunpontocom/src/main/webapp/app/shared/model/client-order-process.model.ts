import { IClientOrder } from '@/shared/model/client-order.model';

export interface IClientOrderProcess {
  id?: number;
  processInstance?: any | null;
  clientOrder?: IClientOrder | null;
}

export class ClientOrderProcess implements IClientOrderProcess {
  constructor(public id?: number, public processInstance?: any | null, public clientOrder?: IClientOrder | null) {}
}
