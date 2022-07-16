import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { IReleveNote } from 'app/entities/releve-note/releve-note.model';
import { Serie } from 'app/entities/enumerations/serie.model';

export interface ISerieEtude {
  id?: number;
  serie?: Serie | null;
  etablissements?: IEtablissement[] | null;
  releveNotes?: IReleveNote[] | null;
}

export class SerieEtude implements ISerieEtude {
  constructor(
    public id?: number,
    public serie?: Serie | null,
    public etablissements?: IEtablissement[] | null,
    public releveNotes?: IReleveNote[] | null
  ) {}
}

export function getSerieEtudeIdentifier(serieEtude: ISerieEtude): number | undefined {
  return serieEtude.id;
}
