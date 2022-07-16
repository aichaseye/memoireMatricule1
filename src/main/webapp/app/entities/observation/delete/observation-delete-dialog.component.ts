import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IObservation } from '../observation.model';
import { ObservationService } from '../service/observation.service';

@Component({
  templateUrl: './observation-delete-dialog.component.html',
})
export class ObservationDeleteDialogComponent {
  observation?: IObservation;

  constructor(protected observationService: ObservationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.observationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
