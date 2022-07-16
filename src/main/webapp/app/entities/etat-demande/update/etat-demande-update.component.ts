import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEtatDemande, EtatDemande } from '../etat-demande.model';
import { EtatDemandeService } from '../service/etat-demande.service';
import { TypeDossier } from 'app/entities/enumerations/type-dossier.model';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-etat-demande-update',
  templateUrl: './etat-demande-update.component.html',
})
export class EtatDemandeUpdateComponent implements OnInit {
  isSaving = false;
  typeDossierValues = Object.keys(TypeDossier);
  statusValues = Object.keys(Status);

  editForm = this.fb.group({
    id: [],
    matricule: [null, [Validators.required]],
    typeDossier: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });

  constructor(protected etatDemandeService: EtatDemandeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatDemande }) => {
      this.updateForm(etatDemande);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatDemande = this.createFromForm();
    if (etatDemande.id !== undefined) {
      this.subscribeToSaveResponse(this.etatDemandeService.update(etatDemande));
    } else {
      this.subscribeToSaveResponse(this.etatDemandeService.create(etatDemande));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatDemande>>): void {
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

  protected updateForm(etatDemande: IEtatDemande): void {
    this.editForm.patchValue({
      id: etatDemande.id,
      matricule: etatDemande.matricule,
      typeDossier: etatDemande.typeDossier,
      status: etatDemande.status,
    });
  }

  protected createFromForm(): IEtatDemande {
    return {
      ...new EtatDemande(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      typeDossier: this.editForm.get(['typeDossier'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
