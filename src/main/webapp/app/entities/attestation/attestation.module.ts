import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AttestationComponent } from './list/attestation.component';
import { AttestationDetailComponent } from './detail/attestation-detail.component';
import { AttestationUpdateComponent } from './update/attestation-update.component';
import { AttestationDeleteDialogComponent } from './delete/attestation-delete-dialog.component';
import { AttestationRoutingModule } from './route/attestation-routing.module';

@NgModule({
  imports: [SharedModule, AttestationRoutingModule],
  declarations: [AttestationComponent, AttestationDetailComponent, AttestationUpdateComponent, AttestationDeleteDialogComponent],
  entryComponents: [AttestationDeleteDialogComponent],
})
export class AttestationModule {}
