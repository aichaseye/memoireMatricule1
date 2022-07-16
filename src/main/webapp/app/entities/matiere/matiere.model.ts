import { IBon } from 'app/entities/bon/bon.model';
import { IImage } from 'app/entities/image/image.model';

export interface IMatiere {
  id?: number;
  nomMatiere?: string;
  reference?: string | null;
  typeMatiere?: string | null;
  etatMatiere?: string | null;
  matriculeMatiere?: string | null;
  bons?: IBon[] | null;
  images?: IImage[] | null;
}

export class Matiere implements IMatiere {
  constructor(
    public id?: number,
    public nomMatiere?: string,
    public reference?: string | null,
    public typeMatiere?: string | null,
    public etatMatiere?: string | null,
    public matriculeMatiere?: string | null,
    public bons?: IBon[] | null,
    public images?: IImage[] | null
  ) {}
}

export function getMatiereIdentifier(matiere: IMatiere): number | undefined {
  return matiere.id;
}
