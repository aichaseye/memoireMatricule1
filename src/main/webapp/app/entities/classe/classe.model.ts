import { IInscription } from 'app/entities/inscription/inscription.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { IProgramme } from 'app/entities/programme/programme.model';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';

export interface IClasse {
  id?: number;
  nomClasse?: string;
  inscriptions?: IInscription[] | null;
  etablissement?: IEtablissement | null;
  programme?: IProgramme | null;
  niveauEtude?: INiveauEtude | null;
}

export class Classe implements IClasse {
  constructor(
    public id?: number,
    public nomClasse?: string,
    public inscriptions?: IInscription[] | null,
    public etablissement?: IEtablissement | null,
    public programme?: IProgramme | null,
    public niveauEtude?: INiveauEtude | null
  ) {}
}

export function getClasseIdentifier(classe: IClasse): number | undefined {
  return classe.id;
}
