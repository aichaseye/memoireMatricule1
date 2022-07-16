import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';

export interface IDiplome {
  id?: number;
  nomDiplome?: NomDiplome | null;
  photoContentType?: string;
  photo?: string;
  etablissement?: IEtablissement | null;
  apprenant?: IApprenant | null;
  etatDemande?: IEtatDemande | null;
}

export class Diplome implements IDiplome {
  constructor(
    public id?: number,
    public nomDiplome?: NomDiplome | null,
    public photoContentType?: string,
    public photo?: string,
    public etablissement?: IEtablissement | null,
    public apprenant?: IApprenant | null,
    public etatDemande?: IEtatDemande | null
  ) {}
}

export function getDiplomeIdentifier(diplome: IDiplome): number | undefined {
  return diplome.id;
}
