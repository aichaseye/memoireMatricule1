import dayjs from 'dayjs/esm';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { IProgramme } from 'app/entities/programme/programme.model';
import { Serie } from 'app/entities/enumerations/serie.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';

export interface IReleveNote {
  id?: number;
  annee?: dayjs.Dayjs;
  moyenne?: number | null;
  serie?: Serie | null;
  filiere?: Filiere | null;
  niveau?: Niveau | null;
  moyenneGenerale?: number | null;
  rang?: string | null;
  noteConduite?: number | null;
  matriculeRel?: string;
  apprenant?: IApprenant | null;
  filiereEtude?: IFiliereEtude | null;
  serieEtude?: ISerieEtude | null;
  demandeDossier?: IDemandeDossier | null;
  programmes?: IProgramme[] | null;
}

export class ReleveNote implements IReleveNote {
  constructor(
    public id?: number,
    public annee?: dayjs.Dayjs,
    public moyenne?: number | null,
    public serie?: Serie | null,
    public filiere?: Filiere | null,
    public niveau?: Niveau | null,
    public moyenneGenerale?: number | null,
    public rang?: string | null,
    public noteConduite?: number | null,
    public matriculeRel?: string,
    public apprenant?: IApprenant | null,
    public filiereEtude?: IFiliereEtude | null,
    public serieEtude?: ISerieEtude | null,
    public demandeDossier?: IDemandeDossier | null,
    public programmes?: IProgramme[] | null
  ) {}
}

export function getReleveNoteIdentifier(releveNote: IReleveNote): number | undefined {
  return releveNote.id;
}
