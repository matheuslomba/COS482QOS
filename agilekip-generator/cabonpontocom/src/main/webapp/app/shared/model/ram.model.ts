export interface IRam {
  id?: number;
  ramName?: string | null;
  ramPrice?: number | null;
}

export class Ram implements IRam {
  constructor(public id?: number, public ramName?: string | null, public ramPrice?: number | null) {}
}
