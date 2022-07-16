import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EtatDemandeComponent } from './list/etat-demande.component';
import { EtatDemandeDetailComponent } from './detail/etat-demande-detail.component';
import { EtatDemandeUpdateComponent } from './update/etat-demande-update.component';
import { EtatDemandeDeleteDialogComponent } from './delete/etat-demande-delete-dialog.component';
import { EtatDemandeRoutingModule } from './route/etat-demande-routing.module';

@NgModule({
  imports: [SharedModule, EtatDemandeRoutingModule],
  declarations: [EtatDemandeComponent, EtatDemandeDetailComponent, EtatDemandeUpdateComponent, EtatDemandeDeleteDialogComponent],
  entryComponents: [EtatDemandeDeleteDialogComponent],
})
export class EtatDemandeModule {}
