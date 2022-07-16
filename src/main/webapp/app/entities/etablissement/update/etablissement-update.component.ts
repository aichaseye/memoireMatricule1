import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEtablissement, Etablissement } from '../etablissement.model';
import { EtablissementService } from '../service/etablissement.service';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { InspectionService } from 'app/entities/inspection/service/inspection.service';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { FiliereEtudeService } from 'app/entities/filiere-etude/service/filiere-etude.service';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { SerieEtudeService } from 'app/entities/serie-etude/service/serie-etude.service';
import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';

@Component({
  selector: 'jhi-etablissement-update',
  templateUrl: './etablissement-update.component.html',
})
export class EtablissementUpdateComponent implements OnInit {
  isSaving = false;
  typeEtabValues = Object.keys(TypeEtab);
  statutEtabValues = Object.keys(StatutEtab);

  localitesSharedCollection: ILocalite[] = [];
  inspectionsSharedCollection: IInspection[] = [];
  filiereEtudesSharedCollection: IFiliereEtude[] = [];
  serieEtudesSharedCollection: ISerieEtude[] = [];

  editForm = this.fb.group({
    id: [],
    nomEtab: [null, [Validators.required]],
    typeEtab: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    adresse: [],
    email: [],
    latitude: [],
    longitude: [],
    matriculeEtab: [],
    localite: [],
    inspection: [],
    filiereEtude: [],
    serieEtude: [],
  });

  constructor(
    protected etablissementService: EtablissementService,
    protected localiteService: LocaliteService,
    protected inspectionService: InspectionService,
    protected filiereEtudeService: FiliereEtudeService,
    protected serieEtudeService: SerieEtudeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablissement }) => {
      this.updateForm(etablissement);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etablissement = this.createFromForm();
    if (etablissement.id !== undefined) {
      this.subscribeToSaveResponse(this.etablissementService.update(etablissement));
    } else {
      this.subscribeToSaveResponse(this.etablissementService.create(etablissement));
    }
  }

  trackLocaliteById(index: number, item: ILocalite): number {
    return item.id!;
  }

  trackInspectionById(index: number, item: IInspection): number {
    return item.id!;
  }

  trackFiliereEtudeById(index: number, item: IFiliereEtude): number {
    return item.id!;
  }

  trackSerieEtudeById(index: number, item: ISerieEtude): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablissement>>): void {
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

  protected updateForm(etablissement: IEtablissement): void {
    this.editForm.patchValue({
      id: etablissement.id,
      nomEtab: etablissement.nomEtab,
      typeEtab: etablissement.typeEtab,
      statut: etablissement.statut,
      adresse: etablissement.adresse,
      email: etablissement.email,
      latitude: etablissement.latitude,
      longitude: etablissement.longitude,
      matriculeEtab: etablissement.matriculeEtab,
      localite: etablissement.localite,
      inspection: etablissement.inspection,
      filiereEtude: etablissement.filiereEtude,
      serieEtude: etablissement.serieEtude,
    });

    this.localitesSharedCollection = this.localiteService.addLocaliteToCollectionIfMissing(
      this.localitesSharedCollection,
      etablissement.localite
    );
    this.inspectionsSharedCollection = this.inspectionService.addInspectionToCollectionIfMissing(
      this.inspectionsSharedCollection,
      etablissement.inspection
    );
    this.filiereEtudesSharedCollection = this.filiereEtudeService.addFiliereEtudeToCollectionIfMissing(
      this.filiereEtudesSharedCollection,
      etablissement.filiereEtude
    );
    this.serieEtudesSharedCollection = this.serieEtudeService.addSerieEtudeToCollectionIfMissing(
      this.serieEtudesSharedCollection,
      etablissement.serieEtude
    );
  }

  protected loadRelationshipsOptions(): void {
    this.localiteService
      .query()
      .pipe(map((res: HttpResponse<ILocalite[]>) => res.body ?? []))
      .pipe(
        map((localites: ILocalite[]) =>
          this.localiteService.addLocaliteToCollectionIfMissing(localites, this.editForm.get('localite')!.value)
        )
      )
      .subscribe((localites: ILocalite[]) => (this.localitesSharedCollection = localites));

    this.inspectionService
      .query()
      .pipe(map((res: HttpResponse<IInspection[]>) => res.body ?? []))
      .pipe(
        map((inspections: IInspection[]) =>
          this.inspectionService.addInspectionToCollectionIfMissing(inspections, this.editForm.get('inspection')!.value)
        )
      )
      .subscribe((inspections: IInspection[]) => (this.inspectionsSharedCollection = inspections));

    this.filiereEtudeService
      .query()
      .pipe(map((res: HttpResponse<IFiliereEtude[]>) => res.body ?? []))
      .pipe(
        map((filiereEtudes: IFiliereEtude[]) =>
          this.filiereEtudeService.addFiliereEtudeToCollectionIfMissing(filiereEtudes, this.editForm.get('filiereEtude')!.value)
        )
      )
      .subscribe((filiereEtudes: IFiliereEtude[]) => (this.filiereEtudesSharedCollection = filiereEtudes));

    this.serieEtudeService
      .query()
      .pipe(map((res: HttpResponse<ISerieEtude[]>) => res.body ?? []))
      .pipe(
        map((serieEtudes: ISerieEtude[]) =>
          this.serieEtudeService.addSerieEtudeToCollectionIfMissing(serieEtudes, this.editForm.get('serieEtude')!.value)
        )
      )
      .subscribe((serieEtudes: ISerieEtude[]) => (this.serieEtudesSharedCollection = serieEtudes));
  }

  protected createFromForm(): IEtablissement {
    return {
      ...new Etablissement(),
      id: this.editForm.get(['id'])!.value,
      nomEtab: this.editForm.get(['nomEtab'])!.value,
      typeEtab: this.editForm.get(['typeEtab'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      email: this.editForm.get(['email'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      matriculeEtab: this.editForm.get(['matriculeEtab'])!.value,
      localite: this.editForm.get(['localite'])!.value,
      inspection: this.editForm.get(['inspection'])!.value,
      filiereEtude: this.editForm.get(['filiereEtude'])!.value,
      serieEtude: this.editForm.get(['serieEtude'])!.value,
    };
  }
}
