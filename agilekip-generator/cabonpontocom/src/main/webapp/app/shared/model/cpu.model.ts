export interface ICpu {
  id?: number;
  cpuName?: string | null;
  cpuPrice?: number | null;
}

export class Cpu implements ICpu {
  constructor(public id?: number, public cpuName?: string | null, public cpuPrice?: number | null) {}
}
