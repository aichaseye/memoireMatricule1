import { IBon } from 'app/entities/bon/bon.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { IPersonnel } from 'app/entities/personnel/personnel.model';
import { IClasse } from 'app/entities/classe/classe.model';
import { IDiplome } from 'app/entities/diplome/diplome.model';
import { IDemandeMatEtab } from 'app/entities/demande-mat-etab/demande-mat-etab.model';
import { ILocalite } from 'app/entities/localite/localite.model';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';

export interface IEtablissement {
  id?: number;
  nomEtab?: string;
  typeEtab?: TypeEtab;
  statut?: StatutEtab;
  adresse?: string | null;
  email?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  matriculeEtab?: string | null;
  bons?: IBon[] | null;
  apprenants?: IApprenant[] | null;
  personnel?: IPersonnel[] | null;
  classes?: IClasse[] | null;
  diplomes?: IDiplome[] | null;
  demandeMatEtabs?: IDemandeMatEtab[] | null;
  localite?: ILocalite | null;
  inspection?: IInspection | null;
  filiereEtude?: IFiliereEtude | null;
  serieEtude?: ISerieEtude | null;
}

export class Etablissement implements IEtablissement {
  constructor(
    public id?: number,
    public nomEtab?: string,
    public typeEtab?: TypeEtab,
    public statut?: StatutEtab,
    public adresse?: string | null,
    public email?: string | null,
    public latitude?: number | null,
    public longitude?: number | null,
    public matriculeEtab?: string | null,
    public bons?: IBon[] | null,
    public apprenants?: IApprenant[] | null,
    public personnel?: IPersonnel[] | null,
    public classes?: IClasse[] | null,
    public diplomes?: IDiplome[] | null,
    public demandeMatEtabs?: IDemandeMatEtab[] | null,
    public localite?: ILocalite | null,
    public inspection?: IInspection | null,
    public filiereEtude?: IFiliereEtude | null,
    public serieEtude?: ISerieEtude | null
  ) {}
}

export function getEtablissementIdentifier(etablissement: IEtablissement): number | undefined {
  return etablissement.id;
}
