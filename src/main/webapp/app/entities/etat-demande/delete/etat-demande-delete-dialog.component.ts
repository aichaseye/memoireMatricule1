import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatDemande } from '../etat-demande.model';
import { EtatDemandeService } from '../service/etat-demande.service';

@Component({
  templateUrl: './etat-demande-delete-dialog.component.html',
})
export class EtatDemandeDeleteDialogComponent {
  etatDemande?: IEtatDemande;

  constructor(protected etatDemandeService: EtatDemandeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatDemandeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
