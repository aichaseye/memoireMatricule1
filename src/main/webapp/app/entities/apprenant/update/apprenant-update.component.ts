import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IApprenant, Apprenant } from '../apprenant.model';
import { ApprenantService } from '../service/apprenant.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { NiveauEtudeService } from 'app/entities/niveau-etude/service/niveau-etude.service';
import { IDemandeMatApp } from 'app/entities/demande-mat-app/demande-mat-app.model';
import { DemandeMatAppService } from 'app/entities/demande-mat-app/service/demande-mat-app.service';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Nationalite } from 'app/entities/enumerations/nationalite.model';

@Component({
  selector: 'jhi-apprenant-update',
  templateUrl: './apprenant-update.component.html',
})
export class ApprenantUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);
  nationaliteValues = Object.keys(Nationalite);

  etablissementsSharedCollection: IEtablissement[] = [];
  niveauEtudesSharedCollection: INiveauEtude[] = [];
  demandeMatAppsSharedCollection: IDemandeMatApp[] = [];
  demandeDossiersSharedCollection: IDemandeDossier[] = [];

  editForm = this.fb.group({
    id: [],
    nomCompletApp: [null, [Validators.required]],
    email: [null, [Validators.required]],
    telephone: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    adresse: [],
    matriculeApp: [],
    sexe: [null, [Validators.required]],
    nationalite: [null, [Validators.required]],
    etablissement: [],
    niveauEtude: [],
    demandeMatApp: [],
    demandeDossier: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected apprenantService: ApprenantService,
    protected etablissementService: EtablissementService,
    protected niveauEtudeService: NiveauEtudeService,
    protected demandeMatAppService: DemandeMatAppService,
    protected demandeDossierService: DemandeDossierService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apprenant }) => {
      this.updateForm(apprenant);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('dbGestionMatricule5App.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apprenant = this.createFromForm();
    if (apprenant.id !== undefined) {
      this.subscribeToSaveResponse(this.apprenantService.update(apprenant));
    } else {
      this.subscribeToSaveResponse(this.apprenantService.create(apprenant));
    }
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  trackNiveauEtudeById(index: number, item: INiveauEtude): number {
    return item.id!;
  }

  trackDemandeMatAppById(index: number, item: IDemandeMatApp): number {
    return item.id!;
  }

  trackDemandeDossierById(index: number, item: IDemandeDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApprenant>>): void {
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

  protected updateForm(apprenant: IApprenant): void {
    this.editForm.patchValue({
      id: apprenant.id,
      nomCompletApp: apprenant.nomCompletApp,
      email: apprenant.email,
      telephone: apprenant.telephone,
      dateNaissance: apprenant.dateNaissance,
      photo: apprenant.photo,
      photoContentType: apprenant.photoContentType,
      adresse: apprenant.adresse,
      matriculeApp: apprenant.matriculeApp,
      sexe: apprenant.sexe,
      nationalite: apprenant.nationalite,
      etablissement: apprenant.etablissement,
      niveauEtude: apprenant.niveauEtude,
      demandeMatApp: apprenant.demandeMatApp,
      demandeDossier: apprenant.demandeDossier,
    });

    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      apprenant.etablissement
    );
    this.niveauEtudesSharedCollection = this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(
      this.niveauEtudesSharedCollection,
      apprenant.niveauEtude
    );
    this.demandeMatAppsSharedCollection = this.demandeMatAppService.addDemandeMatAppToCollectionIfMissing(
      this.demandeMatAppsSharedCollection,
      apprenant.demandeMatApp
    );
    this.demandeDossiersSharedCollection = this.demandeDossierService.addDemandeDossierToCollectionIfMissing(
      this.demandeDossiersSharedCollection,
      apprenant.demandeDossier
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

    this.niveauEtudeService
      .query()
      .pipe(map((res: HttpResponse<INiveauEtude[]>) => res.body ?? []))
      .pipe(
        map((niveauEtudes: INiveauEtude[]) =>
          this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(niveauEtudes, this.editForm.get('niveauEtude')!.value)
        )
      )
      .subscribe((niveauEtudes: INiveauEtude[]) => (this.niveauEtudesSharedCollection = niveauEtudes));

    this.demandeMatAppService
      .query()
      .pipe(map((res: HttpResponse<IDemandeMatApp[]>) => res.body ?? []))
      .pipe(
        map((demandeMatApps: IDemandeMatApp[]) =>
          this.demandeMatAppService.addDemandeMatAppToCollectionIfMissing(demandeMatApps, this.editForm.get('demandeMatApp')!.value)
        )
      )
      .subscribe((demandeMatApps: IDemandeMatApp[]) => (this.demandeMatAppsSharedCollection = demandeMatApps));

    this.demandeDossierService
      .query()
      .pipe(map((res: HttpResponse<IDemandeDossier[]>) => res.body ?? []))
      .pipe(
        map((demandeDossiers: IDemandeDossier[]) =>
          this.demandeDossierService.addDemandeDossierToCollectionIfMissing(demandeDossiers, this.editForm.get('demandeDossier')!.value)
        )
      )
      .subscribe((demandeDossiers: IDemandeDossier[]) => (this.demandeDossiersSharedCollection = demandeDossiers));
  }

  protected createFromForm(): IApprenant {
    return {
      ...new Apprenant(),
      id: this.editForm.get(['id'])!.value,
      nomCompletApp: this.editForm.get(['nomCompletApp'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      matriculeApp: this.editForm.get(['matriculeApp'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      nationalite: this.editForm.get(['nationalite'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
      niveauEtude: this.editForm.get(['niveauEtude'])!.value,
      demandeMatApp: this.editForm.get(['demandeMatApp'])!.value,
      demandeDossier: this.editForm.get(['demandeDossier'])!.value,
    };
  }
}
