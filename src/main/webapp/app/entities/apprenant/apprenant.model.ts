import dayjs from 'dayjs/esm';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { IReleveNote } from 'app/entities/releve-note/releve-note.model';
import { IBulletin } from 'app/entities/bulletin/bulletin.model';
import { IDiplome } from 'app/entities/diplome/diplome.model';
import { IObservation } from 'app/entities/observation/observation.model';
import { ICarteScolaire } from 'app/entities/carte-scolaire/carte-scolaire.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { IDemandeMatApp } from 'app/entities/demande-mat-app/demande-mat-app.model';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Nationalite } from 'app/entities/enumerations/nationalite.model';

export interface IApprenant {
  id?: number;
  nomCompletApp?: string;
  email?: string;
  telephone?: string;
  dateNaissance?: dayjs.Dayjs;
  photoContentType?: string | null;
  photo?: string | null;
  adresse?: string | null;
  matriculeApp?: string | null;
  sexe?: Sexe;
  nationalite?: Nationalite;
  inscriptions?: IInscription[] | null;
  releveNotes?: IReleveNote[] | null;
  bulletins?: IBulletin[] | null;
  diplomes?: IDiplome[] | null;
  observations?: IObservation[] | null;
  carteScolaires?: ICarteScolaire[] | null;
  etablissement?: IEtablissement | null;
  niveauEtude?: INiveauEtude | null;
  demandeMatApp?: IDemandeMatApp | null;
  demandeDossier?: IDemandeDossier | null;
}

export class Apprenant implements IApprenant {
  constructor(
    public id?: number,
    public nomCompletApp?: string,
    public email?: string,
    public telephone?: string,
    public dateNaissance?: dayjs.Dayjs,
    public photoContentType?: string | null,
    public photo?: string | null,
    public adresse?: string | null,
    public matriculeApp?: string | null,
    public sexe?: Sexe,
    public nationalite?: Nationalite,
    public inscriptions?: IInscription[] | null,
    public releveNotes?: IReleveNote[] | null,
    public bulletins?: IBulletin[] | null,
    public diplomes?: IDiplome[] | null,
    public observations?: IObservation[] | null,
    public carteScolaires?: ICarteScolaire[] | null,
    public etablissement?: IEtablissement | null,
    public niveauEtude?: INiveauEtude | null,
    public demandeMatApp?: IDemandeMatApp | null,
    public demandeDossier?: IDemandeDossier | null
  ) {}
}

export function getApprenantIdentifier(apprenant: IApprenant): number | undefined {
  return apprenant.id;
}
