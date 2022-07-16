import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeDossier, getDemandeDossierIdentifier } from '../demande-dossier.model';

export type EntityResponseType = HttpResponse<IDemandeDossier>;
export type EntityArrayResponseType = HttpResponse<IDemandeDossier[]>;

@Injectable({ providedIn: 'root' })
export class DemandeDossierService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-dossiers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeDossier: IDemandeDossier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeDossier);
    return this.http
      .post<IDemandeDossier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(demandeDossier: IDemandeDossier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeDossier);
    return this.http
      .put<IDemandeDossier>(`${this.resourceUrl}/${getDemandeDossierIdentifier(demandeDossier) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(demandeDossier: IDemandeDossier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeDossier);
    return this.http
      .patch<IDemandeDossier>(`${this.resourceUrl}/${getDemandeDossierIdentifier(demandeDossier) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDemandeDossier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDemandeDossier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeDossierToCollectionIfMissing(
    demandeDossierCollection: IDemandeDossier[],
    ...demandeDossiersToCheck: (IDemandeDossier | null | undefined)[]
  ): IDemandeDossier[] {
    const demandeDossiers: IDemandeDossier[] = demandeDossiersToCheck.filter(isPresent);
    if (demandeDossiers.length > 0) {
      const demandeDossierCollectionIdentifiers = demandeDossierCollection.map(
        demandeDossierItem => getDemandeDossierIdentifier(demandeDossierItem)!
      );
      const demandeDossiersToAdd = demandeDossiers.filter(demandeDossierItem => {
        const demandeDossierIdentifier = getDemandeDossierIdentifier(demandeDossierItem);
        if (demandeDossierIdentifier == null || demandeDossierCollectionIdentifiers.includes(demandeDossierIdentifier)) {
          return false;
        }
        demandeDossierCollectionIdentifiers.push(demandeDossierIdentifier);
        return true;
      });
      return [...demandeDossiersToAdd, ...demandeDossierCollection];
    }
    return demandeDossierCollection;
  }

  protected convertDateFromClient(demandeDossier: IDemandeDossier): IDemandeDossier {
    return Object.assign({}, demandeDossier, {
      dateNaissance: demandeDossier.dateNaissance?.isValid() ? demandeDossier.dateNaissance.format(DATE_FORMAT) : undefined,
      annee: demandeDossier.annee?.isValid() ? demandeDossier.annee.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? dayjs(res.body.dateNaissance) : undefined;
      res.body.annee = res.body.annee ? dayjs(res.body.annee) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((demandeDossier: IDemandeDossier) => {
        demandeDossier.dateNaissance = demandeDossier.dateNaissance ? dayjs(demandeDossier.dateNaissance) : undefined;
        demandeDossier.annee = demandeDossier.annee ? dayjs(demandeDossier.annee) : undefined;
      });
    }
    return res;
  }
}
