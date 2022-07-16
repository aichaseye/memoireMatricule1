import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EtatDemandeComponent } from '../list/etat-demande.component';
import { EtatDemandeDetailComponent } from '../detail/etat-demande-detail.component';
import { EtatDemandeUpdateComponent } from '../update/etat-demande-update.component';
import { EtatDemandeRoutingResolveService } from './etat-demande-routing-resolve.service';

const etatDemandeRoute: Routes = [
  {
    path: '',
    component: EtatDemandeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtatDemandeDetailComponent,
    resolve: {
      etatDemande: EtatDemandeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtatDemandeUpdateComponent,
    resolve: {
      etatDemande: EtatDemandeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtatDemandeUpdateComponent,
    resolve: {
      etatDemande: EtatDemandeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(etatDemandeRoute)],
  exports: [RouterModule],
})
export class EtatDemandeRoutingModule {}
