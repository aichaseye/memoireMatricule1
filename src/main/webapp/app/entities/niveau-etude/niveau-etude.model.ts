import { IClasse } from 'app/entities/classe/classe.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';

export interface INiveauEtude {
  id?: number;
  niveau?: Niveau;
  classes?: IClasse[] | null;
  apprenants?: IApprenant[] | null;
}

export class NiveauEtude implements INiveauEtude {
  constructor(public id?: number, public niveau?: Niveau, public classes?: IClasse[] | null, public apprenants?: IApprenant[] | null) {}
}

export function getNiveauEtudeIdentifier(niveauEtude: INiveauEtude): number | undefined {
  return niveauEtude.id;
}
