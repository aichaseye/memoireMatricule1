import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EtablissementService } from '../service/etablissement.service';
import { IEtablissement, Etablissement } from '../etablissement.model';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { InspectionService } from 'app/entities/inspection/service/inspection.service';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { FiliereEtudeService } from 'app/entities/filiere-etude/service/filiere-etude.service';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { SerieEtudeService } from 'app/entities/serie-etude/service/serie-etude.service';

import { EtablissementUpdateComponent } from './etablissement-update.component';

describe('Etablissement Management Update Component', () => {
  let comp: EtablissementUpdateComponent;
  let fixture: ComponentFixture<EtablissementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let etablissementService: EtablissementService;
  let localiteService: LocaliteService;
  let inspectionService: InspectionService;
  let filiereEtudeService: FiliereEtudeService;
  let serieEtudeService: SerieEtudeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EtablissementUpdateComponent],
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
      .overrideTemplate(EtablissementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EtablissementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    etablissementService = TestBed.inject(EtablissementService);
    localiteService = TestBed.inject(LocaliteService);
    inspectionService = TestBed.inject(InspectionService);
    filiereEtudeService = TestBed.inject(FiliereEtudeService);
    serieEtudeService = TestBed.inject(SerieEtudeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Localite query and add missing value', () => {
      const etablissement: IEtablissement = { id: 456 };
      const localite: ILocalite = { id: 860 };
      etablissement.localite = localite;

      const localiteCollection: ILocalite[] = [{ id: 31103 }];
      jest.spyOn(localiteService, 'query').mockReturnValue(of(new HttpResponse({ body: localiteCollection })));
      const additionalLocalites = [localite];
      const expectedCollection: ILocalite[] = [...additionalLocalites, ...localiteCollection];
      jest.spyOn(localiteService, 'addLocaliteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      expect(localiteService.query).toHaveBeenCalled();
      expect(localiteService.addLocaliteToCollectionIfMissing).toHaveBeenCalledWith(localiteCollection, ...additionalLocalites);
      expect(comp.localitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Inspection query and add missing value', () => {
      const etablissement: IEtablissement = { id: 456 };
      const inspection: IInspection = { id: 29576 };
      etablissement.inspection = inspection;

      const inspectionCollection: IInspection[] = [{ id: 52036 }];
      jest.spyOn(inspectionService, 'query').mockReturnValue(of(new HttpResponse({ body: inspectionCollection })));
      const additionalInspections = [inspection];
      const expectedCollection: IInspection[] = [...additionalInspections, ...inspectionCollection];
      jest.spyOn(inspectionService, 'addInspectionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      expect(inspectionService.query).toHaveBeenCalled();
      expect(inspectionService.addInspectionToCollectionIfMissing).toHaveBeenCalledWith(inspectionCollection, ...additionalInspections);
      expect(comp.inspectionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FiliereEtude query and add missing value', () => {
      const etablissement: IEtablissement = { id: 456 };
      const filiereEtude: IFiliereEtude = { id: 79300 };
      etablissement.filiereEtude = filiereEtude;

      const filiereEtudeCollection: IFiliereEtude[] = [{ id: 54704 }];
      jest.spyOn(filiereEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: filiereEtudeCollection })));
      const additionalFiliereEtudes = [filiereEtude];
      const expectedCollection: IFiliereEtude[] = [...additionalFiliereEtudes, ...filiereEtudeCollection];
      jest.spyOn(filiereEtudeService, 'addFiliereEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      expect(filiereEtudeService.query).toHaveBeenCalled();
      expect(filiereEtudeService.addFiliereEtudeToCollectionIfMissing).toHaveBeenCalledWith(
        filiereEtudeCollection,
        ...additionalFiliereEtudes
      );
      expect(comp.filiereEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SerieEtude query and add missing value', () => {
      const etablissement: IEtablissement = { id: 456 };
      const serieEtude: ISerieEtude = { id: 52110 };
      etablissement.serieEtude = serieEtude;

      const serieEtudeCollection: ISerieEtude[] = [{ id: 61672 }];
      jest.spyOn(serieEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: serieEtudeCollection })));
      const additionalSerieEtudes = [serieEtude];
      const expectedCollection: ISerieEtude[] = [...additionalSerieEtudes, ...serieEtudeCollection];
      jest.spyOn(serieEtudeService, 'addSerieEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      expect(serieEtudeService.query).toHaveBeenCalled();
      expect(serieEtudeService.addSerieEtudeToCollectionIfMissing).toHaveBeenCalledWith(serieEtudeCollection, ...additionalSerieEtudes);
      expect(comp.serieEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const etablissement: IEtablissement = { id: 456 };
      const localite: ILocalite = { id: 17242 };
      etablissement.localite = localite;
      const inspection: IInspection = { id: 53283 };
      etablissement.inspection = inspection;
      const filiereEtude: IFiliereEtude = { id: 23119 };
      etablissement.filiereEtude = filiereEtude;
      const serieEtude: ISerieEtude = { id: 56163 };
      etablissement.serieEtude = serieEtude;

      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(etablissement));
      expect(comp.localitesSharedCollection).toContain(localite);
      expect(comp.inspectionsSharedCollection).toContain(inspection);
      expect(comp.filiereEtudesSharedCollection).toContain(filiereEtude);
      expect(comp.serieEtudesSharedCollection).toContain(serieEtude);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Etablissement>>();
      const etablissement = { id: 123 };
      jest.spyOn(etablissementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etablissement }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(etablissementService.update).toHaveBeenCalledWith(etablissement);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Etablissement>>();
      const etablissement = new Etablissement();
      jest.spyOn(etablissementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etablissement }));
      saveSubject.complete();

      // THEN
      expect(etablissementService.create).toHaveBeenCalledWith(etablissement);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Etablissement>>();
      const etablissement = { id: 123 };
      jest.spyOn(etablissementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(etablissementService.update).toHaveBeenCalledWith(etablissement);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLocaliteById', () => {
      it('Should return tracked Localite primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLocaliteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackInspectionById', () => {
      it('Should return tracked Inspection primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInspectionById(0, entity);
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
  });
});
