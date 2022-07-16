import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtatDemande, getEtatDemandeIdentifier } from '../etat-demande.model';

export type EntityResponseType = HttpResponse<IEtatDemande>;
export type EntityArrayResponseType = HttpResponse<IEtatDemande[]>;

@Injectable({ providedIn: 'root' })
export class EtatDemandeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etat-demandes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(etatDemande: IEtatDemande): Observable<EntityResponseType> {
    return this.http.post<IEtatDemande>(this.resourceUrl, etatDemande, { observe: 'response' });
  }

  update(etatDemande: IEtatDemande): Observable<EntityResponseType> {
    return this.http.put<IEtatDemande>(`${this.resourceUrl}/${getEtatDemandeIdentifier(etatDemande) as number}`, etatDemande, {
      observe: 'response',
    });
  }

  partialUpdate(etatDemande: IEtatDemande): Observable<EntityResponseType> {
    return this.http.patch<IEtatDemande>(`${this.resourceUrl}/${getEtatDemandeIdentifier(etatDemande) as number}`, etatDemande, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtatDemande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtatDemande[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEtatDemandeToCollectionIfMissing(
    etatDemandeCollection: IEtatDemande[],
    ...etatDemandesToCheck: (IEtatDemande | null | undefined)[]
  ): IEtatDemande[] {
    const etatDemandes: IEtatDemande[] = etatDemandesToCheck.filter(isPresent);
    if (etatDemandes.length > 0) {
      const etatDemandeCollectionIdentifiers = etatDemandeCollection.map(etatDemandeItem => getEtatDemandeIdentifier(etatDemandeItem)!);
      const etatDemandesToAdd = etatDemandes.filter(etatDemandeItem => {
        const etatDemandeIdentifier = getEtatDemandeIdentifier(etatDemandeItem);
        if (etatDemandeIdentifier == null || etatDemandeCollectionIdentifiers.includes(etatDemandeIdentifier)) {
          return false;
        }
        etatDemandeCollectionIdentifiers.push(etatDemandeIdentifier);
        return true;
      });
      return [...etatDemandesToAdd, ...etatDemandeCollection];
    }
    return etatDemandeCollection;
  }
}
