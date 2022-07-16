import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandeDossierComponent } from './list/demande-dossier.component';
import { DemandeDossierDetailComponent } from './detail/demande-dossier-detail.component';
import { DemandeDossierUpdateComponent } from './update/demande-dossier-update.component';
import { DemandeDossierDeleteDialogComponent } from './delete/demande-dossier-delete-dialog.component';
import { DemandeDossierRoutingModule } from './route/demande-dossier-routing.module';

@NgModule({
  imports: [SharedModule, DemandeDossierRoutingModule],
  declarations: [
    DemandeDossierComponent,
    DemandeDossierDetailComponent,
    DemandeDossierUpdateComponent,
    DemandeDossierDeleteDialogComponent,
  ],
  entryComponents: [DemandeDossierDeleteDialogComponent],
})
export class DemandeDossierModule {}
