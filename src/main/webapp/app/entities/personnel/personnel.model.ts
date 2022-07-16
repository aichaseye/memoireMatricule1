import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { Responsabilite } from 'app/entities/enumerations/responsabilite.model';

export interface IPersonnel {
  id?: number;
  nomPers?: string;
  prenomPers?: string;
  responsabilite?: Responsabilite;
  email?: string;
  etablissement?: IEtablissement | null;
  inspection?: IInspection | null;
}

export class Personnel implements IPersonnel {
  constructor(
    public id?: number,
    public nomPers?: string,
    public prenomPers?: string,
    public responsabilite?: Responsabilite,
    public email?: string,
    public etablissement?: IEtablissement | null,
    public inspection?: IInspection | null
  ) {}
}

export function getPersonnelIdentifier(personnel: IPersonnel): number | undefined {
  return personnel.id;
}
