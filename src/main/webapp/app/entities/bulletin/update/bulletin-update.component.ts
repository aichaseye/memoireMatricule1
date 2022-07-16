import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBulletin, Bulletin } from '../bulletin.model';
import { BulletinService } from '../service/bulletin.service';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';
import { NomSemestre } from 'app/entities/enumerations/nom-semestre.model';
import { Serie } from 'app/entities/enumerations/serie.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';

@Component({
  selector: 'jhi-bulletin-update',
  templateUrl: './bulletin-update.component.html',
})
export class BulletinUpdateComponent implements OnInit {
  isSaving = false;
  nomSemestreValues = Object.keys(NomSemestre);
  serieValues = Object.keys(Serie);
  filiereValues = Object.keys(Filiere);
  niveauValues = Object.keys(Niveau);

  apprenantsSharedCollection: IApprenant[] = [];
  demandeDossiersSharedCollection: IDemandeDossier[] = [];

  editForm = this.fb.group({
    id: [],
    nomsemestre: [],
    annee: [null, [Validators.required]],
    moyenne: [],
    serie: [],
    filiere: [],
    niveau: [],
    moyenneGenerale: [],
    rang: [],
    noteConduite: [],
    matricule: [],
    apprenant: [],
    demandeDossier: [],
  });

  constructor(
    protected bulletinService: BulletinService,
    protected apprenantService: ApprenantService,
    protected demandeDossierService: DemandeDossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bulletin }) => {
      this.updateForm(bulletin);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bulletin = this.createFromForm();
    if (bulletin.id !== undefined) {
      this.subscribeToSaveResponse(this.bulletinService.update(bulletin));
    } else {
      this.subscribeToSaveResponse(this.bulletinService.create(bulletin));
    }
  }

  trackApprenantById(index: number, item: IApprenant): number {
    return item.id!;
  }

  trackDemandeDossierById(index: number, item: IDemandeDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBulletin>>): void {
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

  protected updateForm(bulletin: IBulletin): void {
    this.editForm.patchValue({
      id: bulletin.id,
      nomsemestre: bulletin.nomsemestre,
      annee: bulletin.annee,
      moyenne: bulletin.moyenne,
      serie: bulletin.serie,
      filiere: bulletin.filiere,
      niveau: bulletin.niveau,
      moyenneGenerale: bulletin.moyenneGenerale,
      rang: bulletin.rang,
      noteConduite: bulletin.noteConduite,
      matricule: bulletin.matricule,
      apprenant: bulletin.apprenant,
      demandeDossier: bulletin.demandeDossier,
    });

    this.apprenantsSharedCollection = this.apprenantService.addApprenantToCollectionIfMissing(
      this.apprenantsSharedCollection,
      bulletin.apprenant
    );
    this.demandeDossiersSharedCollection = this.demandeDossierService.addDemandeDossierToCollectionIfMissing(
      this.demandeDossiersSharedCollection,
      bulletin.demandeDossier
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

  protected createFromForm(): IBulletin {
    return {
      ...new Bulletin(),
      id: this.editForm.get(['id'])!.value,
      nomsemestre: this.editForm.get(['nomsemestre'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      moyenne: this.editForm.get(['moyenne'])!.value,
      serie: this.editForm.get(['serie'])!.value,
      filiere: this.editForm.get(['filiere'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      moyenneGenerale: this.editForm.get(['moyenneGenerale'])!.value,
      rang: this.editForm.get(['rang'])!.value,
      noteConduite: this.editForm.get(['noteConduite'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      apprenant: this.editForm.get(['apprenant'])!.value,
      demandeDossier: this.editForm.get(['demandeDossier'])!.value,
    };
  }
}
