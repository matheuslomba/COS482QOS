export interface IClientOrder {
  id?: number;
  orderID?: string | null;
  orderDate?: Date | null;
  clientName?: string | null;
  orderComponents?: string | null;
  orderPrice?: string | null;
  numItems?: number | null;
  assemblyPC?: boolean | null;
}

export class ClientOrder implements IClientOrder {
  constructor(
    public id?: number,
    public orderID?: string | null,
    public orderDate?: Date | null,
    public clientName?: string | null,
    public orderComponents?: string | null,
    public orderPrice?: string | null,
    public numItems?: number | null,
    public assemblyPC?: boolean | null
  ) {
    this.assemblyPC = this.assemblyPC ?? false;
  }
}
