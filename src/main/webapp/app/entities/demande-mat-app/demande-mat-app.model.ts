import dayjs from 'dayjs/esm';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IDemandeMatApp {
  id?: number;
  nom?: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  dateNaissance?: dayjs.Dayjs;
  sexe?: Sexe;
  matriculeApp?: string | null;
  apprenants?: IApprenant[] | null;
}

export class DemandeMatApp implements IDemandeMatApp {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public email?: string,
    public telephone?: string,
    public dateNaissance?: dayjs.Dayjs,
    public sexe?: Sexe,
    public matriculeApp?: string | null,
    public apprenants?: IApprenant[] | null
  ) {}
}

export function getDemandeMatAppIdentifier(demandeMatApp: IDemandeMatApp): number | undefined {
  return demandeMatApp.id;
}
