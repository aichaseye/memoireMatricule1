import dayjs from 'dayjs/esm';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';

export interface ICarteScolaire {
  id?: number;
  nom?: string;
  prenom?: string;
  photoContentType?: string;
  photo?: string;
  annee?: dayjs.Dayjs;
  longeur?: number | null;
  largeur?: number | null;
  demandeDossier?: IDemandeDossier | null;
  apprenant?: IApprenant | null;
}

export class CarteScolaire implements ICarteScolaire {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public photoContentType?: string,
    public photo?: string,
    public annee?: dayjs.Dayjs,
    public longeur?: number | null,
    public largeur?: number | null,
    public demandeDossier?: IDemandeDossier | null,
    public apprenant?: IApprenant | null
  ) {}
}

export function getCarteScolaireIdentifier(carteScolaire: ICarteScolaire): number | undefined {
  return carteScolaire.id;
}
