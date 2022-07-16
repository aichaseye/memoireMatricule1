import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeMatEtab, getDemandeMatEtabIdentifier } from '../demande-mat-etab.model';

export type EntityResponseType = HttpResponse<IDemandeMatEtab>;
export type EntityArrayResponseType = HttpResponse<IDemandeMatEtab[]>;

@Injectable({ providedIn: 'root' })
export class DemandeMatEtabService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-mat-etabs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeMatEtab: IDemandeMatEtab): Observable<EntityResponseType> {
    return this.http.post<IDemandeMatEtab>(this.resourceUrl, demandeMatEtab, { observe: 'response' });
  }

  update(demandeMatEtab: IDemandeMatEtab): Observable<EntityResponseType> {
    return this.http.put<IDemandeMatEtab>(`${this.resourceUrl}/${getDemandeMatEtabIdentifier(demandeMatEtab) as number}`, demandeMatEtab, {
      observe: 'response',
    });
  }

  partialUpdate(demandeMatEtab: IDemandeMatEtab): Observable<EntityResponseType> {
    return this.http.patch<IDemandeMatEtab>(
      `${this.resourceUrl}/${getDemandeMatEtabIdentifier(demandeMatEtab) as number}`,
      demandeMatEtab,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandeMatEtab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandeMatEtab[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeMatEtabToCollectionIfMissing(
    demandeMatEtabCollection: IDemandeMatEtab[],
    ...demandeMatEtabsToCheck: (IDemandeMatEtab | null | undefined)[]
  ): IDemandeMatEtab[] {
    const demandeMatEtabs: IDemandeMatEtab[] = demandeMatEtabsToCheck.filter(isPresent);
    if (demandeMatEtabs.length > 0) {
      const demandeMatEtabCollectionIdentifiers = demandeMatEtabCollection.map(
        demandeMatEtabItem => getDemandeMatEtabIdentifier(demandeMatEtabItem)!
      );
      const demandeMatEtabsToAdd = demandeMatEtabs.filter(demandeMatEtabItem => {
        const demandeMatEtabIdentifier = getDemandeMatEtabIdentifier(demandeMatEtabItem);
        if (demandeMatEtabIdentifier == null || demandeMatEtabCollectionIdentifiers.includes(demandeMatEtabIdentifier)) {
          return false;
        }
        demandeMatEtabCollectionIdentifiers.push(demandeMatEtabIdentifier);
        return true;
      });
      return [...demandeMatEtabsToAdd, ...demandeMatEtabCollection];
    }
    return demandeMatEtabCollection;
  }
}
