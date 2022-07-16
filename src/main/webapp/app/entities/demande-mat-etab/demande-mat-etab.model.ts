import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { Responsabilite } from 'app/entities/enumerations/responsabilite.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';
import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { TypeInspection } from 'app/entities/enumerations/type-inspection.model';

export interface IDemandeMatEtab {
  id?: number;
  nom?: string;
  prenom?: string;
  responsabilite?: Responsabilite;
  nomEtab?: string;
  departement?: NomDep;
  typeEtab?: TypeEtab;
  statut?: StatutEtab;
  typeInsp?: TypeInspection;
  email?: string;
  matriculeEtab?: string | null;
  etablissement?: IEtablissement | null;
}

export class DemandeMatEtab implements IDemandeMatEtab {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public responsabilite?: Responsabilite,
    public nomEtab?: string,
    public departement?: NomDep,
    public typeEtab?: TypeEtab,
    public statut?: StatutEtab,
    public typeInsp?: TypeInspection,
    public email?: string,
    public matriculeEtab?: string | null,
    public etablissement?: IEtablissement | null
  ) {}
}

export function getDemandeMatEtabIdentifier(demandeMatEtab: IDemandeMatEtab): number | undefined {
  return demandeMatEtab.id;
}
