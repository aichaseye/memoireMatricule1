import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AttestationComponent } from '../list/attestation.component';
import { AttestationDetailComponent } from '../detail/attestation-detail.component';
import { AttestationUpdateComponent } from '../update/attestation-update.component';
import { AttestationRoutingResolveService } from './attestation-routing-resolve.service';

const attestationRoute: Routes = [
  {
    path: '',
    component: AttestationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttestationDetailComponent,
    resolve: {
      attestation: AttestationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttestationUpdateComponent,
    resolve: {
      attestation: AttestationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttestationUpdateComponent,
    resolve: {
      attestation: AttestationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(attestationRoute)],
  exports: [RouterModule],
})
export class AttestationRoutingModule {}
