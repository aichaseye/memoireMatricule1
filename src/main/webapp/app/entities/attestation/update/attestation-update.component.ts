import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAttestation, Attestation } from '../attestation.model';
import { AttestationService } from '../service/attestation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { EtatDemandeService } from 'app/entities/etat-demande/service/etat-demande.service';

@Component({
  selector: 'jhi-attestation-update',
  templateUrl: './attestation-update.component.html',
})
export class AttestationUpdateComponent implements OnInit {
  isSaving = false;

  etatDemandesSharedCollection: IEtatDemande[] = [];

  editForm = this.fb.group({
    id: [],
    photo: [null, [Validators.required]],
    photoContentType: [],
    etatDemande: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected attestationService: AttestationService,
    protected etatDemandeService: EtatDemandeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attestation }) => {
      this.updateForm(attestation);

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
    const attestation = this.createFromForm();
    if (attestation.id !== undefined) {
      this.subscribeToSaveResponse(this.attestationService.update(attestation));
    } else {
      this.subscribeToSaveResponse(this.attestationService.create(attestation));
    }
  }

  trackEtatDemandeById(index: number, item: IEtatDemande): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttestation>>): void {
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

  protected updateForm(attestation: IAttestation): void {
    this.editForm.patchValue({
      id: attestation.id,
      photo: attestation.photo,
      photoContentType: attestation.photoContentType,
      etatDemande: attestation.etatDemande,
    });

    this.etatDemandesSharedCollection = this.etatDemandeService.addEtatDemandeToCollectionIfMissing(
      this.etatDemandesSharedCollection,
      attestation.etatDemande
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IAttestation {
    return {
      ...new Attestation(),
      id: this.editForm.get(['id'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      etatDemande: this.editForm.get(['etatDemande'])!.value,
    };
  }
}
