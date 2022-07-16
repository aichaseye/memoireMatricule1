import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPersonnel, Personnel } from '../personnel.model';
import { PersonnelService } from '../service/personnel.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { InspectionService } from 'app/entities/inspection/service/inspection.service';
import { Responsabilite } from 'app/entities/enumerations/responsabilite.model';

@Component({
  selector: 'jhi-personnel-update',
  templateUrl: './personnel-update.component.html',
})
export class PersonnelUpdateComponent implements OnInit {
  isSaving = false;
  responsabiliteValues = Object.keys(Responsabilite);

  etablissementsSharedCollection: IEtablissement[] = [];
  inspectionsSharedCollection: IInspection[] = [];

  editForm = this.fb.group({
    id: [],
    nomPers: [null, [Validators.required]],
    prenomPers: [null, [Validators.required]],
    responsabilite: [null, [Validators.required]],
    email: [null, [Validators.required]],
    etablissement: [],
    inspection: [],
  });

  constructor(
    protected personnelService: PersonnelService,
    protected etablissementService: EtablissementService,
    protected inspectionService: InspectionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personnel }) => {
      this.updateForm(personnel);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personnel = this.createFromForm();
    if (personnel.id !== undefined) {
      this.subscribeToSaveResponse(this.personnelService.update(personnel));
    } else {
      this.subscribeToSaveResponse(this.personnelService.create(personnel));
    }
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  trackInspectionById(index: number, item: IInspection): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonnel>>): void {
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

  protected updateForm(personnel: IPersonnel): void {
    this.editForm.patchValue({
      id: personnel.id,
      nomPers: personnel.nomPers,
      prenomPers: personnel.prenomPers,
      responsabilite: personnel.responsabilite,
      email: personnel.email,
      etablissement: personnel.etablissement,
      inspection: personnel.inspection,
    });

    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      personnel.etablissement
    );
    this.inspectionsSharedCollection = this.inspectionService.addInspectionToCollectionIfMissing(
      this.inspectionsSharedCollection,
      personnel.inspection
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

    this.inspectionService
      .query()
      .pipe(map((res: HttpResponse<IInspection[]>) => res.body ?? []))
      .pipe(
        map((inspections: IInspection[]) =>
          this.inspectionService.addInspectionToCollectionIfMissing(inspections, this.editForm.get('inspection')!.value)
        )
      )
      .subscribe((inspections: IInspection[]) => (this.inspectionsSharedCollection = inspections));
  }

  protected createFromForm(): IPersonnel {
    return {
      ...new Personnel(),
      id: this.editForm.get(['id'])!.value,
      nomPers: this.editForm.get(['nomPers'])!.value,
      prenomPers: this.editForm.get(['prenomPers'])!.value,
      responsabilite: this.editForm.get(['responsabilite'])!.value,
      email: this.editForm.get(['email'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
      inspection: this.editForm.get(['inspection'])!.value,
    };
  }
}
