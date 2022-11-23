export interface IPowerSource {
  id?: number;
  powerSourceName?: string | null;
  powerSourcePrice?: number | null;
}

export class PowerSource implements IPowerSource {
  constructor(public id?: number, public powerSourceName?: string | null, public powerSourcePrice?: number | null) {}
}
