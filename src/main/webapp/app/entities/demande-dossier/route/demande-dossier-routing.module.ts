import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeDossierComponent } from '../list/demande-dossier.component';
import { DemandeDossierDetailComponent } from '../detail/demande-dossier-detail.component';
import { DemandeDossierUpdateComponent } from '../update/demande-dossier-update.component';
import { DemandeDossierRoutingResolveService } from './demande-dossier-routing-resolve.service';

const demandeDossierRoute: Routes = [
  {
    path: '',
    component: DemandeDossierComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeDossierDetailComponent,
    resolve: {
      demandeDossier: DemandeDossierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeDossierUpdateComponent,
    resolve: {
      demandeDossier: DemandeDossierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeDossierUpdateComponent,
    resolve: {
      demandeDossier: DemandeDossierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeDossierRoute)],
  exports: [RouterModule],
})
export class DemandeDossierRoutingModule {}
