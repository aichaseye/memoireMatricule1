import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IObservation, Observation } from '../observation.model';
import { ObservationService } from '../service/observation.service';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';
import { Asuduite } from 'app/entities/enumerations/asuduite.model';
import { Ponctualite } from 'app/entities/enumerations/ponctualite.model';
import { Apte } from 'app/entities/enumerations/apte.model';

@Component({
  selector: 'jhi-observation-update',
  templateUrl: './observation-update.component.html',
})
export class ObservationUpdateComponent implements OnInit {
  isSaving = false;
  asuduiteValues = Object.keys(Asuduite);
  ponctualiteValues = Object.keys(Ponctualite);
  apteValues = Object.keys(Apte);

  apprenantsSharedCollection: IApprenant[] = [];

  editForm = this.fb.group({
    id: [],
    asuduite: [],
    ponctualite: [],
    apte: [],
    apprenant: [],
  });

  constructor(
    protected observationService: ObservationService,
    protected apprenantService: ApprenantService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observation }) => {
      this.updateForm(observation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const observation = this.createFromForm();
    if (observation.id !== undefined) {
      this.subscribeToSaveResponse(this.observationService.update(observation));
    } else {
      this.subscribeToSaveResponse(this.observationService.create(observation));
    }
  }

  trackApprenantById(index: number, item: IApprenant): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(observation: IObservation): void {
    this.editForm.patchValue({
      id: observation.id,
      asuduite: observation.asuduite,
      ponctualite: observation.ponctualite,
      apte: observation.apte,
      apprenant: observation.apprenant,
    });

    this.apprenantsSharedCollection = this.apprenantService.addApprenantToCollectionIfMissing(
      this.apprenantsSharedCollection,
      observation.apprenant
    );
  }

  protected loadRelationshipsOptions(): void {
    this.apprenantService
      .query()
      .pipe(map((res: HttpResponse<IApprenant[]>) => res.body ?? []))
      .pipe(
        map((apprenants: IApprenant[]) =>
          this.apprenantService.addApprenantToCollectionIfMissing(apprenants, this.editForm.get('apprenant')!.value)
        )
      )
      .subscribe((apprenants: IApprenant[]) => (this.apprenantsSharedCollection = apprenants));
  }

  protected createFromForm(): IObservation {
    return {
      ...new Observation(),
      id: this.editForm.get(['id'])!.value,
      asuduite: this.editForm.get(['asuduite'])!.value,
      ponctualite: this.editForm.get(['ponctualite'])!.value,
      apte: this.editForm.get(['apte'])!.value,
      apprenant: this.editForm.get(['apprenant'])!.value,
    };
  }
}
