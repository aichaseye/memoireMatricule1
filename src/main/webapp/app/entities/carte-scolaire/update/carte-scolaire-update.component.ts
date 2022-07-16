import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICarteScolaire, CarteScolaire } from '../carte-scolaire.model';
import { CarteScolaireService } from '../service/carte-scolaire.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';

@Component({
  selector: 'jhi-carte-scolaire-update',
  templateUrl: './carte-scolaire-update.component.html',
})
export class CarteScolaireUpdateComponent implements OnInit {
  isSaving = false;

  demandeDossiersSharedCollection: IDemandeDossier[] = [];
  apprenantsSharedCollection: IApprenant[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    photo: [null, [Validators.required]],
    photoContentType: [],
    annee: [null, [Validators.required]],
    longeur: [],
    largeur: [],
    demandeDossier: [],
    apprenant: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected carteScolaireService: CarteScolaireService,
    protected demandeDossierService: DemandeDossierService,
    protected apprenantService: ApprenantService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carteScolaire }) => {
      this.updateForm(carteScolaire);

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
    const carteScolaire = this.createFromForm();
    if (carteScolaire.id !== undefined) {
      this.subscribeToSaveResponse(this.carteScolaireService.update(carteScolaire));
    } else {
      this.subscribeToSaveResponse(this.carteScolaireService.create(carteScolaire));
    }
  }

  trackDemandeDossierById(index: number, item: IDemandeDossier): number {
    return item.id!;
  }

  trackApprenantById(index: number, item: IApprenant): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarteScolaire>>): void {
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

  protected updateForm(carteScolaire: ICarteScolaire): void {
    this.editForm.patchValue({
      id: carteScolaire.id,
      nom: carteScolaire.nom,
      prenom: carteScolaire.prenom,
      photo: carteScolaire.photo,
      photoContentType: carteScolaire.photoContentType,
      annee: carteScolaire.annee,
      longeur: carteScolaire.longeur,
      largeur: carteScolaire.largeur,
      demandeDossier: carteScolaire.demandeDossier,
      apprenant: carteScolaire.apprenant,
    });

    this.demandeDossiersSharedCollection = this.demandeDossierService.addDemandeDossierToCollectionIfMissing(
      this.demandeDossiersSharedCollection,
      carteScolaire.demandeDossier
    );
    this.apprenantsSharedCollection = this.apprenantService.addApprenantToCollectionIfMissing(
      this.apprenantsSharedCollection,
      carteScolaire.apprenant
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandeDossierService
      .query()
      .pipe(map((res: HttpResponse<IDemandeDossier[]>) => res.body ?? []))
      .pipe(
        map((demandeDossiers: IDemandeDossier[]) =>
          this.demandeDossierService.addDemandeDossierToCollectionIfMissing(demandeDossiers, this.editForm.get('demandeDossier')!.value)
        )
      )
      .subscribe((demandeDossiers: IDemandeDossier[]) => (this.demandeDossiersSharedCollection = demandeDossiers));

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

  protected createFromForm(): ICarteScolaire {
    return {
      ...new CarteScolaire(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      longeur: this.editForm.get(['longeur'])!.value,
      largeur: this.editForm.get(['largeur'])!.value,
      demandeDossier: this.editForm.get(['demandeDossier'])!.value,
      apprenant: this.editForm.get(['apprenant'])!.value,
    };
  }
}
