import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { NomReg } from 'app/entities/enumerations/nom-reg.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';

export interface ILocalite {
  id?: number;
  region?: NomReg;
  departement?: NomDep;
  commune?: string;
  etablissements?: IEtablissement[] | null;
}

export class Localite implements ILocalite {
  constructor(
    public id?: number,
    public region?: NomReg,
    public departement?: NomDep,
    public commune?: string,
    public etablissements?: IEtablissement[] | null
  ) {}
}

export function getLocaliteIdentifier(localite: ILocalite): number | undefined {
  return localite.id;
}
