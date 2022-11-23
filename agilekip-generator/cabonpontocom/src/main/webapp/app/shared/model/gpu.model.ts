export interface IGpu {
  id?: number;
  gpuName?: string | null;
  gpuPrice?: number | null;
}

export class Gpu implements IGpu {
  constructor(public id?: number, public gpuName?: string | null, public gpuPrice?: number | null) {}
}
