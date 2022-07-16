import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { IDiplome } from 'app/entities/diplome/diplome.model';
import { IAttestation } from 'app/entities/attestation/attestation.model';
import { TypeDossier } from 'app/entities/enumerations/type-dossier.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IEtatDemande {
  id?: number;
  matricule?: string;
  typeDossier?: TypeDossier;
  status?: Status;
  demandeDossiers?: IDemandeDossier[] | null;
  diplomes?: IDiplome[] | null;
  attestations?: IAttestation[] | null;
}

export class EtatDemande implements IEtatDemande {
  constructor(
    public id?: number,
    public matricule?: string,
    public typeDossier?: TypeDossier,
    public status?: Status,
    public demandeDossiers?: IDemandeDossier[] | null,
    public diplomes?: IDiplome[] | null,
    public attestations?: IAttestation[] | null
  ) {}
}

export function getEtatDemandeIdentifier(etatDemande: IEtatDemande): number | undefined {
  return etatDemande.id;
}
