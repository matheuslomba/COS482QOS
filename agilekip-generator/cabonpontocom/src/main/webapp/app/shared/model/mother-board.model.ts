export interface IMotherBoard {
  id?: number;
  motherBoardName?: string | null;
  motherBoardPrice?: number | null;
}

export class MotherBoard implements IMotherBoard {
  constructor(public id?: number, public motherBoardName?: string | null, public motherBoardPrice?: number | null) {}
}
