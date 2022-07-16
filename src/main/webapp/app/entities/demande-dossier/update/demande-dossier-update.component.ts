import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandeDossier, DemandeDossier } from '../demande-dossier.model';
import { DemandeDossierService } from '../service/demande-dossier.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { EtatDemandeService } from 'app/entities/etat-demande/service/etat-demande.service';
import { TypeDossier } from 'app/entities/enumerations/type-dossier.model';
import { Serie } from 'app/entities/enumerations/serie.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';
import { NomSemestre } from 'app/entities/enumerations/nom-semestre.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';
import { TypeReleve } from 'app/entities/enumerations/type-releve.model';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';

@Component({
  selector: 'jhi-demande-dossier-update',
  templateUrl: './demande-dossier-update.component.html',
})
export class DemandeDossierUpdateComponent implements OnInit {
  isSaving = false;
  typeDossierValues = Object.keys(TypeDossier);
  serieValues = Object.keys(Serie);
  filiereValues = Object.keys(Filiere);
  nomSemestreValues = Object.keys(NomSemestre);
  niveauValues = Object.keys(Niveau);
  typeReleveValues = Object.keys(TypeReleve);
  nomDiplomeValues = Object.keys(NomDiplome);

  etatDemandesSharedCollection: IEtatDemande[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    email: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    matricule: [null, [Validators.required]],
    typeDossier: [null, [Validators.required]],
    annee: [null, [Validators.required]],
    serie: [],
    filiere: [null, [Validators.required]],
    nomSemestre: [null, [Validators.required]],
    niveau: [null, [Validators.required]],
    typeReleve: [null, [Validators.required]],
    nomDiplome: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    etatDemande: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected demandeDossierService: DemandeDossierService,
    protected etatDemandeService: EtatDemandeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeDossier }) => {
      this.updateForm(demandeDossier);

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
    const demandeDossier = this.createFromForm();
    if (demandeDossier.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeDossierService.update(demandeDossier));
    } else {
      this.subscribeToSaveResponse(this.demandeDossierService.create(demandeDossier));
    }
  }

  trackEtatDemandeById(index: number, item: IEtatDemande): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeDossier>>): void {
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

  protected updateForm(demandeDossier: IDemandeDossier): void {
    this.editForm.patchValue({
      id: demandeDossier.id,
      nom: demandeDossier.nom,
      prenom: demandeDossier.prenom,
      email: demandeDossier.email,
      dateNaissance: demandeDossier.dateNaissance,
      matricule: demandeDossier.matricule,
      typeDossier: demandeDossier.typeDossier,
      annee: demandeDossier.annee,
      serie: demandeDossier.serie,
      filiere: demandeDossier.filiere,
      nomSemestre: demandeDossier.nomSemestre,
      niveau: demandeDossier.niveau,
      typeReleve: demandeDossier.typeReleve,
      nomDiplome: demandeDossier.nomDiplome,
      photo: demandeDossier.photo,
      photoContentType: demandeDossier.photoContentType,
      etatDemande: demandeDossier.etatDemande,
    });

    this.etatDemandesSharedCollection = this.etatDemandeService.addEtatDemandeToCollectionIfMissing(
      this.etatDemandesSharedCollection,
      demandeDossier.etatDemande
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

  protected createFromForm(): IDemandeDossier {
    return {
      ...new DemandeDossier(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      typeDossier: this.editForm.get(['typeDossier'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      serie: this.editForm.get(['serie'])!.value,
      filiere: this.editForm.get(['filiere'])!.value,
      nomSemestre: this.editForm.get(['nomSemestre'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      typeReleve: this.editForm.get(['typeReleve'])!.value,
      nomDiplome: this.editForm.get(['nomDiplome'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      etatDemande: this.editForm.get(['etatDemande'])!.value,
    };
  }
}
