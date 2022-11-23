export interface IHd {
  id?: number;
  hdName?: string | null;
  hdPrice?: number | null;
}

export class Hd implements IHd {
  constructor(public id?: number, public hdName?: string | null, public hdPrice?: number | null) {}
}
