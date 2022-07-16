import dayjs from 'dayjs/esm';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { IBulletin } from 'app/entities/bulletin/bulletin.model';
import { IReleveNote } from 'app/entities/releve-note/releve-note.model';
import { ICarteScolaire } from 'app/entities/carte-scolaire/carte-scolaire.model';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { TypeDossier } from 'app/entities/enumerations/type-dossier.model';
import { Serie } from 'app/entities/enumerations/serie.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';
import { NomSemestre } from 'app/entities/enumerations/nom-semestre.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';
import { TypeReleve } from 'app/entities/enumerations/type-releve.model';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';

export interface IDemandeDossier {
  id?: number;
  nom?: string;
  prenom?: string;
  email?: string;
  dateNaissance?: dayjs.Dayjs;
  matricule?: string;
  typeDossier?: TypeDossier;
  annee?: dayjs.Dayjs;
  serie?: Serie | null;
  filiere?: Filiere;
  nomSemestre?: NomSemestre;
  niveau?: Niveau;
  typeReleve?: TypeReleve;
  nomDiplome?: NomDiplome;
  photoContentType?: string | null;
  photo?: string | null;
  apprenants?: IApprenant[] | null;
  bulletins?: IBulletin[] | null;
  releveNotes?: IReleveNote[] | null;
  carteScolaires?: ICarteScolaire[] | null;
  etatDemande?: IEtatDemande | null;
}

export class DemandeDossier implements IDemandeDossier {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public email?: string,
    public dateNaissance?: dayjs.Dayjs,
    public matricule?: string,
    public typeDossier?: TypeDossier,
    public annee?: dayjs.Dayjs,
    public serie?: Serie | null,
    public filiere?: Filiere,
    public nomSemestre?: NomSemestre,
    public niveau?: Niveau,
    public typeReleve?: TypeReleve,
    public nomDiplome?: NomDiplome,
    public photoContentType?: string | null,
    public photo?: string | null,
    public apprenants?: IApprenant[] | null,
    public bulletins?: IBulletin[] | null,
    public releveNotes?: IReleveNote[] | null,
    public carteScolaires?: ICarteScolaire[] | null,
    public etatDemande?: IEtatDemande | null
  ) {}
}

export function getDemandeDossierIdentifier(demandeDossier: IDemandeDossier): number | undefined {
  return demandeDossier.id;
}
