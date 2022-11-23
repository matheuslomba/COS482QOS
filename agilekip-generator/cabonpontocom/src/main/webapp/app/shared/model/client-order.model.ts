import { IMotherBoard } from '@/shared/model/mother-board.model';
import { ICpu } from '@/shared/model/cpu.model';
import { IGpu } from '@/shared/model/gpu.model';
import { IRam } from '@/shared/model/ram.model';
import { IHd } from '@/shared/model/hd.model';
import { IPowerSource } from '@/shared/model/power-source.model';

export interface IClientOrder {
  id?: number;
  orderID?: string | null;
  orderDate?: Date | null;
  clientName?: string | null;
  clientEmail?: string | null;
  orderPrice?: number | null;
  numComponents?: number | null;
  payment?: string | null;
  proceedToCheckout?: boolean | null;
  assemblyPC?: boolean | null;
  deliveryAdd?: string | null;
  isCompatible?: boolean | null;
  motherBoard?: IMotherBoard | null;
  cpu?: ICpu | null;
  gpu?: IGpu | null;
  ram?: IRam | null;
  hd?: IHd | null;
  powerSource?: IPowerSource | null;
}

export class ClientOrder implements IClientOrder {
  constructor(
    public id?: number,
    public orderID?: string | null,
    public orderDate?: Date | null,
    public clientName?: string | null,
    public clientEmail?: string | null,
    public orderPrice?: number | null,
    public numComponents?: number | null,
    public payment?: string | null,
    public proceedToCheckout?: boolean | null,
    public assemblyPC?: boolean | null,
    public deliveryAdd?: string | null,
    public isCompatible?: boolean | null,
    public motherBoard?: IMotherBoard | null,
    public cpu?: ICpu | null,
    public gpu?: IGpu | null,
    public ram?: IRam | null,
    public hd?: IHd | null,
    public powerSource?: IPowerSource | null
  ) {
    this.proceedToCheckout = this.proceedToCheckout ?? false;
    this.assemblyPC = this.assemblyPC ?? false;
    this.isCompatible = this.isCompatible ?? false;
  }
}
