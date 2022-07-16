import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandeMatEtab, DemandeMatEtab } from '../demande-mat-etab.model';
import { DemandeMatEtabService } from '../service/demande-mat-etab.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { Responsabilite } from 'app/entities/enumerations/responsabilite.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';
import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { TypeInspection } from 'app/entities/enumerations/type-inspection.model';

@Component({
  selector: 'jhi-demande-mat-etab-update',
  templateUrl: './demande-mat-etab-update.component.html',
})
export class DemandeMatEtabUpdateComponent implements OnInit {
  isSaving = false;
  responsabiliteValues = Object.keys(Responsabilite);
  nomDepValues = Object.keys(NomDep);
  typeEtabValues = Object.keys(TypeEtab);
  statutEtabValues = Object.keys(StatutEtab);
  typeInspectionValues = Object.keys(TypeInspection);

  etablissementsSharedCollection: IEtablissement[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    responsabilite: [null, [Validators.required]],
    nomEtab: [null, [Validators.required]],
    departement: [null, [Validators.required]],
    typeEtab: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    typeInsp: [null, [Validators.required]],
    email: [null, [Validators.required]],
    matriculeEtab: [],
    etablissement: [],
  });

  constructor(
    protected demandeMatEtabService: DemandeMatEtabService,
    protected etablissementService: EtablissementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeMatEtab }) => {
      this.updateForm(demandeMatEtab);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeMatEtab = this.createFromForm();
    if (demandeMatEtab.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeMatEtabService.update(demandeMatEtab));
    } else {
      this.subscribeToSaveResponse(this.demandeMatEtabService.create(demandeMatEtab));
    }
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeMatEtab>>): void {
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

  protected updateForm(demandeMatEtab: IDemandeMatEtab): void {
    this.editForm.patchValue({
      id: demandeMatEtab.id,
      nom: demandeMatEtab.nom,
      prenom: demandeMatEtab.prenom,
      responsabilite: demandeMatEtab.responsabilite,
      nomEtab: demandeMatEtab.nomEtab,
      departement: demandeMatEtab.departement,
      typeEtab: demandeMatEtab.typeEtab,
      statut: demandeMatEtab.statut,
      typeInsp: demandeMatEtab.typeInsp,
      email: demandeMatEtab.email,
      matriculeEtab: demandeMatEtab.matriculeEtab,
      etablissement: demandeMatEtab.etablissement,
    });

    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      demandeMatEtab.etablissement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.etablissementService
      .query()
      .pipe(map((res: HttpResponse<IEtablissement[]>) => res.body ?? []))
      .pipe(
        map((etablissements: IEtablissement[]) =>
          this.etablissementService.addEtablissementToCollectionIfMissing(etablissements, this.editForm.get('etablissement')!.value)
        )
      )
      .subscribe((etablissements: IEtablissement[]) => (this.etablissementsSharedCollection = etablissements));
  }

  protected createFromForm(): IDemandeMatEtab {
    return {
      ...new DemandeMatEtab(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      responsabilite: this.editForm.get(['responsabilite'])!.value,
      nomEtab: this.editForm.get(['nomEtab'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      typeEtab: this.editForm.get(['typeEtab'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      typeInsp: this.editForm.get(['typeInsp'])!.value,
      email: this.editForm.get(['email'])!.value,
      matriculeEtab: this.editForm.get(['matriculeEtab'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
    };
  }
}
