import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeDossier } from '../demande-dossier.model';
import { DemandeDossierService } from '../service/demande-dossier.service';

@Component({
  templateUrl: './demande-dossier-delete-dialog.component.html',
})
export class DemandeDossierDeleteDialogComponent {
  demandeDossier?: IDemandeDossier;

  constructor(protected demandeDossierService: DemandeDossierService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeDossierService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
