import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeDossier, DemandeDossier } from '../demande-dossier.model';
import { DemandeDossierService } from '../service/demande-dossier.service';

@Injectable({ providedIn: 'root' })
export class DemandeDossierRoutingResolveService implements Resolve<IDemandeDossier> {
  constructor(protected service: DemandeDossierService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeDossier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeDossier: HttpResponse<DemandeDossier>) => {
          if (demandeDossier.body) {
            return of(demandeDossier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandeDossier());
  }
}
