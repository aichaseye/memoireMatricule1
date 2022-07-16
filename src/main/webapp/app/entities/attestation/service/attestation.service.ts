import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAttestation, getAttestationIdentifier } from '../attestation.model';

export type EntityResponseType = HttpResponse<IAttestation>;
export type EntityArrayResponseType = HttpResponse<IAttestation[]>;

@Injectable({ providedIn: 'root' })
export class AttestationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/attestations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(attestation: IAttestation): Observable<EntityResponseType> {
    return this.http.post<IAttestation>(this.resourceUrl, attestation, { observe: 'response' });
  }

  update(attestation: IAttestation): Observable<EntityResponseType> {
    return this.http.put<IAttestation>(`${this.resourceUrl}/${getAttestationIdentifier(attestation) as number}`, attestation, {
      observe: 'response',
    });
  }

  partialUpdate(attestation: IAttestation): Observable<EntityResponseType> {
    return this.http.patch<IAttestation>(`${this.resourceUrl}/${getAttestationIdentifier(attestation) as number}`, attestation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttestation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttestation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAttestationToCollectionIfMissing(
    attestationCollection: IAttestation[],
    ...attestationsToCheck: (IAttestation | null | undefined)[]
  ): IAttestation[] {
    const attestations: IAttestation[] = attestationsToCheck.filter(isPresent);
    if (attestations.length > 0) {
      const attestationCollectionIdentifiers = attestationCollection.map(attestationItem => getAttestationIdentifier(attestationItem)!);
      const attestationsToAdd = attestations.filter(attestationItem => {
        const attestationIdentifier = getAttestationIdentifier(attestationItem);
        if (attestationIdentifier == null || attestationCollectionIdentifiers.includes(attestationIdentifier)) {
          return false;
        }
        attestationCollectionIdentifiers.push(attestationIdentifier);
        return true;
      });
      return [...attestationsToAdd, ...attestationCollection];
    }
    return attestationCollection;
  }
}
