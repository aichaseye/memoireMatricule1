import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttestation } from '../attestation.model';
import { AttestationService } from '../service/attestation.service';

@Component({
  templateUrl: './attestation-delete-dialog.component.html',
})
export class AttestationDeleteDialogComponent {
  attestation?: IAttestation;

  constructor(protected attestationService: AttestationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attestationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
