import { IClasse } from 'app/entities/classe/classe.model';
import { IReleveNote } from 'app/entities/releve-note/releve-note.model';

export interface IProgramme {
  id?: number;
  nomModule?: string | null;
  coef?: number | null;
  note?: number | null;
  classes?: IClasse[] | null;
  releveNotes?: IReleveNote[] | null;
}

export class Programme implements IProgramme {
  constructor(
    public id?: number,
    public nomModule?: string | null,
    public coef?: number | null,
    public note?: number | null,
    public classes?: IClasse[] | null,
    public releveNotes?: IReleveNote[] | null
  ) {}
}

export function getProgrammeIdentifier(programme: IProgramme): number | undefined {
  return programme.id;
}
