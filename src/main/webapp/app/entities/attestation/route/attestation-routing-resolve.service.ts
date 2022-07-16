import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAttestation, Attestation } from '../attestation.model';
import { AttestationService } from '../service/attestation.service';

@Injectable({ providedIn: 'root' })
export class AttestationRoutingResolveService implements Resolve<IAttestation> {
  constructor(protected service: AttestationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttestation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((attestation: HttpResponse<Attestation>) => {
          if (attestation.body) {
            return of(attestation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Attestation());
  }
}
