import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IReleveNote, ReleveNote } from '../releve-note.model';
import { ReleveNoteService } from '../service/releve-note.service';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { FiliereEtudeService } from 'app/entities/filiere-etude/service/filiere-etude.service';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { SerieEtudeService } from 'app/entities/serie-etude/service/serie-etude.service';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';
import { Serie } from 'app/entities/enumerations/serie.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';

@Component({
  selector: 'jhi-releve-note-update',
  templateUrl: './releve-note-update.component.html',
})
export class ReleveNoteUpdateComponent implements OnInit {
  isSaving = false;
  serieValues = Object.keys(Serie);
  filiereValues = Object.keys(Filiere);
  niveauValues = Object.keys(Niveau);

  apprenantsSharedCollection: IApprenant[] = [];
  filiereEtudesSharedCollection: IFiliereEtude[] = [];
  serieEtudesSharedCollection: ISerieEtude[] = [];
  demandeDossiersSharedCollection: IDemandeDossier[] = [];

  editForm = this.fb.group({
    id: [],
    annee: [null, [Validators.required]],
    moyenne: [],
    serie: [],
    filiere: [],
    niveau: [],
    moyenneGenerale: [],
    rang: [],
    noteConduite: [],
    matriculeRel: [null, [Validators.required]],
    apprenant: [],
    filiereEtude: [],
    serieEtude: [],
    demandeDossier: [],
  });

  constructor(
    protected releveNoteService: ReleveNoteService,
    protected apprenantService: ApprenantService,
    protected filiereEtudeService: FiliereEtudeService,
    protected serieEtudeService: SerieEtudeService,
    protected demandeDossierService: DemandeDossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ releveNote }) => {
      this.updateForm(releveNote);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const releveNote = this.createFromForm();
    if (releveNote.id !== undefined) {
      this.subscribeToSaveResponse(this.releveNoteService.update(releveNote));
    } else {
      this.subscribeToSaveResponse(this.releveNoteService.create(releveNote));
    }
  }

  trackApprenantById(index: number, item: IApprenant): number {
    return item.id!;
  }

  trackFiliereEtudeById(index: number, item: IFiliereEtude): number {
    return item.id!;
  }

  trackSerieEtudeById(index: number, item: ISerieEtude): number {
    return item.id!;
  }

  trackDemandeDossierById(index: number, item: IDemandeDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReleveNote>>): void {
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

  protected updateForm(releveNote: IReleveNote): void {
    this.editForm.patchValue({
      id: releveNote.id,
      annee: releveNote.annee,
      moyenne: releveNote.moyenne,
      serie: releveNote.serie,
      filiere: releveNote.filiere,
      niveau: releveNote.niveau,
      moyenneGenerale: releveNote.moyenneGenerale,
      rang: releveNote.rang,
      noteConduite: releveNote.noteConduite,
      matriculeRel: releveNote.matriculeRel,
      apprenant: releveNote.apprenant,
      filiereEtude: releveNote.filiereEtude,
      serieEtude: releveNote.serieEtude,
      demandeDossier: releveNote.demandeDossier,
    });

    this.apprenantsSharedCollection = this.apprenantService.addApprenantToCollectionIfMissing(
      this.apprenantsSharedCollection,
      releveNote.apprenant
    );
    this.filiereEtudesSharedCollection = this.filiereEtudeService.addFiliereEtudeToCollectionIfMissing(
      this.filiereEtudesSharedCollection,
      releveNote.filiereEtude
    );
    this.serieEtudesSharedCollection = this.serieEtudeService.addSerieEtudeToCollectionIfMissing(
      this.serieEtudesSharedCollection,
      releveNote.serieEtude
    );
    this.demandeDossiersSharedCollection = this.demandeDossierService.addDemandeDossierToCollectionIfMissing(
      this.demandeDossiersSharedCollection,
      releveNote.demandeDossier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.apprenantService
      .query()
      .pipe(map((res: HttpResponse<IApprenant[]>) => res.body ?? []))
      .pipe(
        map((apprenants: IApprenant[]) =>
          this.apprenantService.addApprenantToCollectionIfMissing(apprenants, this.editForm.get('apprenant')!.value)
        )
      )
      .subscribe((apprenants: IApprenant[]) => (this.apprenantsSharedCollection = apprenants));

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

  protected createFromForm(): IReleveNote {
    return {
      ...new ReleveNote(),
      id: this.editForm.get(['id'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      moyenne: this.editForm.get(['moyenne'])!.value,
      serie: this.editForm.get(['serie'])!.value,
      filiere: this.editForm.get(['filiere'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      moyenneGenerale: this.editForm.get(['moyenneGenerale'])!.value,
      rang: this.editForm.get(['rang'])!.value,
      noteConduite: this.editForm.get(['noteConduite'])!.value,
      matriculeRel: this.editForm.get(['matriculeRel'])!.value,
      apprenant: this.editForm.get(['apprenant'])!.value,
      filiereEtude: this.editForm.get(['filiereEtude'])!.value,
      serieEtude: this.editForm.get(['serieEtude'])!.value,
      demandeDossier: this.editForm.get(['demandeDossier'])!.value,
    };
  }
}
