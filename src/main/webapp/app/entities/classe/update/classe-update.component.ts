import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IClasse, Classe } from '../classe.model';
import { ClasseService } from '../service/classe.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { IProgramme } from 'app/entities/programme/programme.model';
import { ProgrammeService } from 'app/entities/programme/service/programme.service';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { NiveauEtudeService } from 'app/entities/niveau-etude/service/niveau-etude.service';

@Component({
  selector: 'jhi-classe-update',
  templateUrl: './classe-update.component.html',
})
export class ClasseUpdateComponent implements OnInit {
  isSaving = false;

  etablissementsSharedCollection: IEtablissement[] = [];
  programmesSharedCollection: IProgramme[] = [];
  niveauEtudesSharedCollection: INiveauEtude[] = [];

  editForm = this.fb.group({
    id: [],
    nomClasse: [null, [Validators.required]],
    etablissement: [],
    programme: [],
    niveauEtude: [],
  });

  constructor(
    protected classeService: ClasseService,
    protected etablissementService: EtablissementService,
    protected programmeService: ProgrammeService,
    protected niveauEtudeService: NiveauEtudeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classe }) => {
      this.updateForm(classe);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classe = this.createFromForm();
    if (classe.id !== undefined) {
      this.subscribeToSaveResponse(this.classeService.update(classe));
    } else {
      this.subscribeToSaveResponse(this.classeService.create(classe));
    }
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  trackProgrammeById(index: number, item: IProgramme): number {
    return item.id!;
  }

  trackNiveauEtudeById(index: number, item: INiveauEtude): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasse>>): void {
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

  protected updateForm(classe: IClasse): void {
    this.editForm.patchValue({
      id: classe.id,
      nomClasse: classe.nomClasse,
      etablissement: classe.etablissement,
      programme: classe.programme,
      niveauEtude: classe.niveauEtude,
    });

    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      classe.etablissement
    );
    this.programmesSharedCollection = this.programmeService.addProgrammeToCollectionIfMissing(
      this.programmesSharedCollection,
      classe.programme
    );
    this.niveauEtudesSharedCollection = this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(
      this.niveauEtudesSharedCollection,
      classe.niveauEtude
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

    this.programmeService
      .query()
      .pipe(map((res: HttpResponse<IProgramme[]>) => res.body ?? []))
      .pipe(
        map((programmes: IProgramme[]) =>
          this.programmeService.addProgrammeToCollectionIfMissing(programmes, this.editForm.get('programme')!.value)
        )
      )
      .subscribe((programmes: IProgramme[]) => (this.programmesSharedCollection = programmes));

    this.niveauEtudeService
      .query()
      .pipe(map((res: HttpResponse<INiveauEtude[]>) => res.body ?? []))
      .pipe(
        map((niveauEtudes: INiveauEtude[]) =>
          this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(niveauEtudes, this.editForm.get('niveauEtude')!.value)
        )
      )
      .subscribe((niveauEtudes: INiveauEtude[]) => (this.niveauEtudesSharedCollection = niveauEtudes));
  }

  protected createFromForm(): IClasse {
    return {
      ...new Classe(),
      id: this.editForm.get(['id'])!.value,
      nomClasse: this.editForm.get(['nomClasse'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
      programme: this.editForm.get(['programme'])!.value,
      niveauEtude: this.editForm.get(['niveauEtude'])!.value,
    };
  }
}
