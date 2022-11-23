export interface IClientOrder {
  id?: number;
  orderID?: string | null;
  orderDate?: Date | null;
  clientName?: string | null;
  orderPrice?: number | null;
  numComponents?: number | null;
  payment?: string | null;
  proceedToCheckout?: boolean | null;
  assemblyPC?: boolean | null;
  deliveryAdd?: string | null;
  isCompatible?: boolean | null;
}

export class ClientOrder implements IClientOrder {
  constructor(
    public id?: number,
    public orderID?: string | null,
    public orderDate?: Date | null,
    public clientName?: string | null,
    public orderPrice?: number | null,
    public numComponents?: number | null,
    public payment?: string | null,
    public proceedToCheckout?: boolean | null,
    public assemblyPC?: boolean | null,
    public deliveryAdd?: string | null,
    public isCompatible?: boolean | null
  ) {
    this.proceedToCheckout = this.proceedToCheckout ?? false;
    this.assemblyPC = this.assemblyPC ?? false;
    this.isCompatible = this.isCompatible ?? false;
  }
}
