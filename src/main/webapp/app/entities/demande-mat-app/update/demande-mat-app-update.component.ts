import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDemandeMatApp, DemandeMatApp } from '../demande-mat-app.model';
import { DemandeMatAppService } from '../service/demande-mat-app.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';

@Component({
  selector: 'jhi-demande-mat-app-update',
  templateUrl: './demande-mat-app-update.component.html',
})
export class DemandeMatAppUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    email: [null, [Validators.required]],
    telephone: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    sexe: [null, [Validators.required]],
    matriculeApp: [],
  });

  constructor(protected demandeMatAppService: DemandeMatAppService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeMatApp }) => {
      this.updateForm(demandeMatApp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeMatApp = this.createFromForm();
    if (demandeMatApp.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeMatAppService.update(demandeMatApp));
    } else {
      this.subscribeToSaveResponse(this.demandeMatAppService.create(demandeMatApp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeMatApp>>): void {
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

  protected updateForm(demandeMatApp: IDemandeMatApp): void {
    this.editForm.patchValue({
      id: demandeMatApp.id,
      nom: demandeMatApp.nom,
      prenom: demandeMatApp.prenom,
      email: demandeMatApp.email,
      telephone: demandeMatApp.telephone,
      dateNaissance: demandeMatApp.dateNaissance,
      sexe: demandeMatApp.sexe,
      matriculeApp: demandeMatApp.matriculeApp,
    });
  }

  protected createFromForm(): IDemandeMatApp {
    return {
      ...new DemandeMatApp(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      matriculeApp: this.editForm.get(['matriculeApp'])!.value,
    };
  }
}
