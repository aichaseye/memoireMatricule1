import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReleveNoteService } from '../service/releve-note.service';
import { IReleveNote, ReleveNote } from '../releve-note.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { FiliereEtudeService } from 'app/entities/filiere-etude/service/filiere-etude.service';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { SerieEtudeService } from 'app/entities/serie-etude/service/serie-etude.service';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';

import { ReleveNoteUpdateComponent } from './releve-note-update.component';

describe('ReleveNote Management Update Component', () => {
  let comp: ReleveNoteUpdateComponent;
  let fixture: ComponentFixture<ReleveNoteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let releveNoteService: ReleveNoteService;
  let apprenantService: ApprenantService;
  let filiereEtudeService: FiliereEtudeService;
  let serieEtudeService: SerieEtudeService;
  let demandeDossierService: DemandeDossierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReleveNoteUpdateComponent],
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
      .overrideTemplate(ReleveNoteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReleveNoteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    releveNoteService = TestBed.inject(ReleveNoteService);
    apprenantService = TestBed.inject(ApprenantService);
    filiereEtudeService = TestBed.inject(FiliereEtudeService);
    serieEtudeService = TestBed.inject(SerieEtudeService);
    demandeDossierService = TestBed.inject(DemandeDossierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Apprenant query and add missing value', () => {
      const releveNote: IReleveNote = { id: 456 };
      const apprenant: IApprenant = { id: 17717 };
      releveNote.apprenant = apprenant;

      const apprenantCollection: IApprenant[] = [{ id: 38240 }];
      jest.spyOn(apprenantService, 'query').mockReturnValue(of(new HttpResponse({ body: apprenantCollection })));
      const additionalApprenants = [apprenant];
      const expectedCollection: IApprenant[] = [...additionalApprenants, ...apprenantCollection];
      jest.spyOn(apprenantService, 'addApprenantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      expect(apprenantService.query).toHaveBeenCalled();
      expect(apprenantService.addApprenantToCollectionIfMissing).toHaveBeenCalledWith(apprenantCollection, ...additionalApprenants);
      expect(comp.apprenantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FiliereEtude query and add missing value', () => {
      const releveNote: IReleveNote = { id: 456 };
      const filiereEtude: IFiliereEtude = { id: 17810 };
      releveNote.filiereEtude = filiereEtude;

      const filiereEtudeCollection: IFiliereEtude[] = [{ id: 5692 }];
      jest.spyOn(filiereEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: filiereEtudeCollection })));
      const additionalFiliereEtudes = [filiereEtude];
      const expectedCollection: IFiliereEtude[] = [...additionalFiliereEtudes, ...filiereEtudeCollection];
      jest.spyOn(filiereEtudeService, 'addFiliereEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      expect(filiereEtudeService.query).toHaveBeenCalled();
      expect(filiereEtudeService.addFiliereEtudeToCollectionIfMissing).toHaveBeenCalledWith(
        filiereEtudeCollection,
        ...additionalFiliereEtudes
      );
      expect(comp.filiereEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SerieEtude query and add missing value', () => {
      const releveNote: IReleveNote = { id: 456 };
      const serieEtude: ISerieEtude = { id: 8239 };
      releveNote.serieEtude = serieEtude;

      const serieEtudeCollection: ISerieEtude[] = [{ id: 15858 }];
      jest.spyOn(serieEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: serieEtudeCollection })));
      const additionalSerieEtudes = [serieEtude];
      const expectedCollection: ISerieEtude[] = [...additionalSerieEtudes, ...serieEtudeCollection];
      jest.spyOn(serieEtudeService, 'addSerieEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      expect(serieEtudeService.query).toHaveBeenCalled();
      expect(serieEtudeService.addSerieEtudeToCollectionIfMissing).toHaveBeenCalledWith(serieEtudeCollection, ...additionalSerieEtudes);
      expect(comp.serieEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeDossier query and add missing value', () => {
      const releveNote: IReleveNote = { id: 456 };
      const demandeDossier: IDemandeDossier = { id: 6860 };
      releveNote.demandeDossier = demandeDossier;

      const demandeDossierCollection: IDemandeDossier[] = [{ id: 80043 }];
      jest.spyOn(demandeDossierService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDossierCollection })));
      const additionalDemandeDossiers = [demandeDossier];
      const expectedCollection: IDemandeDossier[] = [...additionalDemandeDossiers, ...demandeDossierCollection];
      jest.spyOn(demandeDossierService, 'addDemandeDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      expect(demandeDossierService.query).toHaveBeenCalled();
      expect(demandeDossierService.addDemandeDossierToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDossierCollection,
        ...additionalDemandeDossiers
      );
      expect(comp.demandeDossiersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const releveNote: IReleveNote = { id: 456 };
      const apprenant: IApprenant = { id: 29473 };
      releveNote.apprenant = apprenant;
      const filiereEtude: IFiliereEtude = { id: 53646 };
      releveNote.filiereEtude = filiereEtude;
      const serieEtude: ISerieEtude = { id: 86925 };
      releveNote.serieEtude = serieEtude;
      const demandeDossier: IDemandeDossier = { id: 12800 };
      releveNote.demandeDossier = demandeDossier;

      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(releveNote));
      expect(comp.apprenantsSharedCollection).toContain(apprenant);
      expect(comp.filiereEtudesSharedCollection).toContain(filiereEtude);
      expect(comp.serieEtudesSharedCollection).toContain(serieEtude);
      expect(comp.demandeDossiersSharedCollection).toContain(demandeDossier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReleveNote>>();
      const releveNote = { id: 123 };
      jest.spyOn(releveNoteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: releveNote }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(releveNoteService.update).toHaveBeenCalledWith(releveNote);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReleveNote>>();
      const releveNote = new ReleveNote();
      jest.spyOn(releveNoteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: releveNote }));
      saveSubject.complete();

      // THEN
      expect(releveNoteService.create).toHaveBeenCalledWith(releveNote);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReleveNote>>();
      const releveNote = { id: 123 };
      jest.spyOn(releveNoteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ releveNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(releveNoteService.update).toHaveBeenCalledWith(releveNote);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackApprenantById', () => {
      it('Should return tracked Apprenant primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackApprenantById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFiliereEtudeById', () => {
      it('Should return tracked FiliereEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFiliereEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSerieEtudeById', () => {
      it('Should return tracked SerieEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSerieEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDemandeDossierById', () => {
      it('Should return tracked DemandeDossier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeDossierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
