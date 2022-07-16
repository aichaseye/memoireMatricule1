import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';

export interface IAttestation {
  id?: number;
  photoContentType?: string;
  photo?: string;
  etatDemande?: IEtatDemande | null;
}

export class Attestation implements IAttestation {
  constructor(public id?: number, public photoContentType?: string, public photo?: string, public etatDemande?: IEtatDemande | null) {}
}

export function getAttestationIdentifier(attestation: IAttestation): number | undefined {
  return attestation.id;
}
