import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { Asuduite } from 'app/entities/enumerations/asuduite.model';
import { Ponctualite } from 'app/entities/enumerations/ponctualite.model';
import { Apte } from 'app/entities/enumerations/apte.model';

export interface IObservation {
  id?: number;
  asuduite?: Asuduite | null;
  ponctualite?: Ponctualite | null;
  apte?: Apte | null;
  apprenant?: IApprenant | null;
}

export class Observation implements IObservation {
  constructor(
    public id?: number,
    public asuduite?: Asuduite | null,
    public ponctualite?: Ponctualite | null,
    public apte?: Apte | null,
    public apprenant?: IApprenant | null
  ) {}
}

export function getObservationIdentifier(observation: IObservation): number | undefined {
  return observation.id;
}
