import dayjs from 'dayjs/esm';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { NomSemestre } from 'app/entities/enumerations/nom-semestre.model';
import { Serie } from 'app/entities/enumerations/serie.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';

export interface IBulletin {
  id?: number;
  nomsemestre?: NomSemestre | null;
  annee?: dayjs.Dayjs;
  moyenne?: number | null;
  serie?: Serie | null;
  filiere?: Filiere | null;
  niveau?: Niveau | null;
  moyenneGenerale?: number | null;
  rang?: string | null;
  noteConduite?: number | null;
  matricule?: string | null;
  apprenant?: IApprenant | null;
  demandeDossier?: IDemandeDossier | null;
}

export class Bulletin implements IBulletin {
  constructor(
    public id?: number,
    public nomsemestre?: NomSemestre | null,
    public annee?: dayjs.Dayjs,
    public moyenne?: number | null,
    public serie?: Serie | null,
    public filiere?: Filiere | null,
    public niveau?: Niveau | null,
    public moyenneGenerale?: number | null,
    public rang?: string | null,
    public noteConduite?: number | null,
    public matricule?: string | null,
    public apprenant?: IApprenant | null,
    public demandeDossier?: IDemandeDossier | null
  ) {}
}

export function getBulletinIdentifier(bulletin: IBulletin): number | undefined {
  return bulletin.id;
}
