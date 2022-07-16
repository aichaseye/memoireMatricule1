import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgrammeService } from '../service/programme.service';
import { IProgramme, Programme } from '../programme.model';
import { IReleveNote } from 'app/entities/releve-note/releve-note.model';
import { ReleveNoteService } from 'app/entities/releve-note/service/releve-note.service';

import { ProgrammeUpdateComponent } from './programme-update.component';

describe('Programme Management Update Component', () => {
  let comp: ProgrammeUpdateComponent;
  let fixture: ComponentFixture<ProgrammeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programmeService: ProgrammeService;
  let releveNoteService: ReleveNoteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgrammeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProgrammeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgrammeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programmeService = TestBed.inject(ProgrammeService);
    releveNoteService = TestBed.inject(ReleveNoteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ReleveNote query and add missing value', () => {
      const programme: IProgramme = { id: 456 };
      const releveNotes: IReleveNote[] = [{ id: 92188 }];
      programme.releveNotes = releveNotes;

      const releveNoteCollection: IReleveNote[] = [{ id: 89362 }];
      jest.spyOn(releveNoteService, 'query').mockReturnValue(of(new HttpResponse({ body: releveNoteCollection })));
      const additionalReleveNotes = [...releveNotes];
      const expectedCollection: IReleveNote[] = [...additionalReleveNotes, ...releveNoteCollection];
      jest.spyOn(releveNoteService, 'addReleveNoteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programme });
      comp.ngOnInit();

      expect(releveNoteService.query).toHaveBeenCalled();
      expect(releveNoteService.addReleveNoteToCollectionIfMissing).toHaveBeenCalledWith(releveNoteCollection, ...additionalReleveNotes);
      expect(comp.releveNotesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programme: IProgramme = { id: 456 };
      const releveNotes: IReleveNote = { id: 90360 };
      programme.releveNotes = [releveNotes];

      activatedRoute.data = of({ programme });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(programme));
      expect(comp.releveNotesSharedCollection).toContain(releveNotes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Programme>>();
      const programme = { id: 123 };
      jest.spyOn(programmeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programme });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programme }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(programmeService.update).toHaveBeenCalledWith(programme);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Programme>>();
      const programme = new Programme();
      jest.spyOn(programmeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programme });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programme }));
      saveSubject.complete();

      // THEN
      expect(programmeService.create).toHaveBeenCalledWith(programme);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Programme>>();
      const programme = { id: 123 };
      jest.spyOn(programmeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programme });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programmeService.update).toHaveBeenCalledWith(programme);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackReleveNoteById', () => {
      it('Should return tracked ReleveNote primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReleveNoteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedReleveNote', () => {
      it('Should return option if no ReleveNote is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedReleveNote(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected ReleveNote for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedReleveNote(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this ReleveNote is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedReleveNote(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
