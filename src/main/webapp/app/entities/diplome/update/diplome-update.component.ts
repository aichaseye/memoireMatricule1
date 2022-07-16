import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDiplome, Diplome } from '../diplome.model';
import { DiplomeService } from '../service/diplome.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { EtatDemandeService } from 'app/entities/etat-demande/service/etat-demande.service';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';

@Component({
  selector: 'jhi-diplome-update',
  templateUrl: './diplome-update.component.html',
})
export class DiplomeUpdateComponent implements OnInit {
  isSaving = false;
  nomDiplomeValues = Object.keys(NomDiplome);

  etablissementsSharedCollection: IEtablissement[] = [];
  apprenantsSharedCollection: IApprenant[] = [];
  etatDemandesSharedCollection: IEtatDemande[] = [];

  editForm = this.fb.group({
    id: [],
    nomDiplome: [],
    photo: [null, [Validators.required]],
    photoContentType: [],
    etablissement: [],
    apprenant: [],
    etatDemande: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected diplomeService: DiplomeService,
    protected etablissementService: EtablissementService,
    protected apprenantService: ApprenantService,
    protected etatDemandeService: EtatDemandeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diplome }) => {
      this.updateForm(diplome);

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
    const diplome = this.createFromForm();
    if (diplome.id !== undefined) {
      this.subscribeToSaveResponse(this.diplomeService.update(diplome));
    } else {
      this.subscribeToSaveResponse(this.diplomeService.create(diplome));
    }
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  trackApprenantById(index: number, item: IApprenant): number {
    return item.id!;
  }

  trackEtatDemandeById(index: number, item: IEtatDemande): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiplome>>): void {
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

  protected updateForm(diplome: IDiplome): void {
    this.editForm.patchValue({
      id: diplome.id,
      nomDiplome: diplome.nomDiplome,
      photo: diplome.photo,
      photoContentType: diplome.photoContentType,
      etablissement: diplome.etablissement,
      apprenant: diplome.apprenant,
      etatDemande: diplome.etatDemande,
    });

    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      diplome.etablissement
    );
    this.apprenantsSharedCollection = this.apprenantService.addApprenantToCollectionIfMissing(
      this.apprenantsSharedCollection,
      diplome.apprenant
    );
    this.etatDemandesSharedCollection = this.etatDemandeService.addEtatDemandeToCollectionIfMissing(
      this.etatDemandesSharedCollection,
      diplome.etatDemande
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

    this.apprenantService
      .query()
      .pipe(map((res: HttpResponse<IApprenant[]>) => res.body ?? []))
      .pipe(
        map((apprenants: IApprenant[]) =>
          this.apprenantService.addApprenantToCollectionIfMissing(apprenants, this.editForm.get('apprenant')!.value)
        )
      )
      .subscribe((apprenants: IApprenant[]) => (this.apprenantsSharedCollection = apprenants));

    this.etatDemandeService
      .query()
      .pipe(map((res: HttpResponse<IEtatDemande[]>) => res.body ?? []))
      .pipe(
        map((etatDemandes: IEtatDemande[]) =>
          this.etatDemandeService.addEtatDemandeToCollectionIfMissing(etatDemandes, this.editForm.get('etatDemande')!.value)
        )
      )
      .subscribe((etatDemandes: IEtatDemande[]) => (this.etatDemandesSharedCollection = etatDemandes));
  }

  protected createFromForm(): IDiplome {
    return {
      ...new Diplome(),
      id: this.editForm.get(['id'])!.value,
      nomDiplome: this.editForm.get(['nomDiplome'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
      apprenant: this.editForm.get(['apprenant'])!.value,
      etatDemande: this.editForm.get(['etatDemande'])!.value,
    };
  }
}
