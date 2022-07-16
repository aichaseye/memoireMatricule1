import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IObservation, getObservationIdentifier } from '../observation.model';

export type EntityResponseType = HttpResponse<IObservation>;
export type EntityArrayResponseType = HttpResponse<IObservation[]>;

@Injectable({ providedIn: 'root' })
export class ObservationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/observations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(observation: IObservation): Observable<EntityResponseType> {
    return this.http.post<IObservation>(this.resourceUrl, observation, { observe: 'response' });
  }

  update(observation: IObservation): Observable<EntityResponseType> {
    return this.http.put<IObservation>(`${this.resourceUrl}/${getObservationIdentifier(observation) as number}`, observation, {
      observe: 'response',
    });
  }

  partialUpdate(observation: IObservation): Observable<EntityResponseType> {
    return this.http.patch<IObservation>(`${this.resourceUrl}/${getObservationIdentifier(observation) as number}`, observation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObservation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObservation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addObservationToCollectionIfMissing(
    observationCollection: IObservation[],
    ...observationsToCheck: (IObservation | null | undefined)[]
  ): IObservation[] {
    const observations: IObservation[] = observationsToCheck.filter(isPresent);
    if (observations.length > 0) {
      const observationCollectionIdentifiers = observationCollection.map(observationItem => getObservationIdentifier(observationItem)!);
      const observationsToAdd = observations.filter(observationItem => {
        const observationIdentifier = getObservationIdentifier(observationItem);
        if (observationIdentifier == null || observationCollectionIdentifiers.includes(observationIdentifier)) {
          return false;
        }
        observationCollectionIdentifiers.push(observationIdentifier);
        return true;
      });
      return [...observationsToAdd, ...observationCollection];
    }
    return observationCollection;
  }
}
