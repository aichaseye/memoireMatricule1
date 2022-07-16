import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtatDemande, EtatDemande } from '../etat-demande.model';
import { EtatDemandeService } from '../service/etat-demande.service';

@Injectable({ providedIn: 'root' })
export class EtatDemandeRoutingResolveService implements Resolve<IEtatDemande> {
  constructor(protected service: EtatDemandeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatDemande> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((etatDemande: HttpResponse<EtatDemande>) => {
          if (etatDemande.body) {
            return of(etatDemande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatDemande());
  }
}
