import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProgramme, Programme } from '../programme.model';
import { ProgrammeService } from '../service/programme.service';
import { IReleveNote } from 'app/entities/releve-note/releve-note.model';
import { ReleveNoteService } from 'app/entities/releve-note/service/releve-note.service';

@Component({
  selector: 'jhi-programme-update',
  templateUrl: './programme-update.component.html',
})
export class ProgrammeUpdateComponent implements OnInit {
  isSaving = false;

  releveNotesSharedCollection: IReleveNote[] = [];

  editForm = this.fb.group({
    id: [],
    nomModule: [],
    coef: [],
    note: [],
    releveNotes: [],
  });

  constructor(
    protected programmeService: ProgrammeService,
    protected releveNoteService: ReleveNoteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programme }) => {
      this.updateForm(programme);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programme = this.createFromForm();
    if (programme.id !== undefined) {
      this.subscribeToSaveResponse(this.programmeService.update(programme));
    } else {
      this.subscribeToSaveResponse(this.programmeService.create(programme));
    }
  }

  trackReleveNoteById(index: number, item: IReleveNote): number {
    return item.id!;
  }

  getSelectedReleveNote(option: IReleveNote, selectedVals?: IReleveNote[]): IReleveNote {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramme>>): void {
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

  protected updateForm(programme: IProgramme): void {
    this.editForm.patchValue({
      id: programme.id,
      nomModule: programme.nomModule,
      coef: programme.coef,
      note: programme.note,
      releveNotes: programme.releveNotes,
    });

    this.releveNotesSharedCollection = this.releveNoteService.addReleveNoteToCollectionIfMissing(
      this.releveNotesSharedCollection,
      ...(programme.releveNotes ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.releveNoteService
      .query()
      .pipe(map((res: HttpResponse<IReleveNote[]>) => res.body ?? []))
      .pipe(
        map((releveNotes: IReleveNote[]) =>
          this.releveNoteService.addReleveNoteToCollectionIfMissing(releveNotes, ...(this.editForm.get('releveNotes')!.value ?? []))
        )
      )
      .subscribe((releveNotes: IReleveNote[]) => (this.releveNotesSharedCollection = releveNotes));
  }

  protected createFromForm(): IProgramme {
    return {
      ...new Programme(),
      id: this.editForm.get(['id'])!.value,
      nomModule: this.editForm.get(['nomModule'])!.value,
      coef: this.editForm.get(['coef'])!.value,
      note: this.editForm.get(['note'])!.value,
      releveNotes: this.editForm.get(['releveNotes'])!.value,
    };
  }
}
