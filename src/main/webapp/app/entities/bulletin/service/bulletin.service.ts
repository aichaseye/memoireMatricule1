import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBulletin, getBulletinIdentifier } from '../bulletin.model';

export type EntityResponseType = HttpResponse<IBulletin>;
export type EntityArrayResponseType = HttpResponse<IBulletin[]>;

@Injectable({ providedIn: 'root' })
export class BulletinService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bulletins');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bulletin: IBulletin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bulletin);
    return this.http
      .post<IBulletin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bulletin: IBulletin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bulletin);
    return this.http
      .put<IBulletin>(`${this.resourceUrl}/${getBulletinIdentifier(bulletin) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bulletin: IBulletin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bulletin);
    return this.http
      .patch<IBulletin>(`${this.resourceUrl}/${getBulletinIdentifier(bulletin) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBulletin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBulletin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBulletinToCollectionIfMissing(bulletinCollection: IBulletin[], ...bulletinsToCheck: (IBulletin | null | undefined)[]): IBulletin[] {
    const bulletins: IBulletin[] = bulletinsToCheck.filter(isPresent);
    if (bulletins.length > 0) {
      const bulletinCollectionIdentifiers = bulletinCollection.map(bulletinItem => getBulletinIdentifier(bulletinItem)!);
      const bulletinsToAdd = bulletins.filter(bulletinItem => {
        const bulletinIdentifier = getBulletinIdentifier(bulletinItem);
        if (bulletinIdentifier == null || bulletinCollectionIdentifiers.includes(bulletinIdentifier)) {
          return false;
        }
        bulletinCollectionIdentifiers.push(bulletinIdentifier);
        return true;
      });
      return [...bulletinsToAdd, ...bulletinCollection];
    }
    return bulletinCollection;
  }

  protected convertDateFromClient(bulletin: IBulletin): IBulletin {
    return Object.assign({}, bulletin, {
      annee: bulletin.annee?.isValid() ? bulletin.annee.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.annee = res.body.annee ? dayjs(res.body.annee) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bulletin: IBulletin) => {
        bulletin.annee = bulletin.annee ? dayjs(bulletin.annee) : undefined;
      });
    }
    return res;
  }
}
