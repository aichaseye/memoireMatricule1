import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClasseService } from '../service/classe.service';
import { IClasse, Classe } from '../classe.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { IProgramme } from 'app/entities/programme/programme.model';
import { ProgrammeService } from 'app/entities/programme/service/programme.service';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { NiveauEtudeService } from 'app/entities/niveau-etude/service/niveau-etude.service';

import { ClasseUpdateComponent } from './classe-update.component';

describe('Classe Management Update Component', () => {
  let comp: ClasseUpdateComponent;
  let fixture: ComponentFixture<ClasseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let classeService: ClasseService;
  let etablissementService: EtablissementService;
  let programmeService: ProgrammeService;
  let niveauEtudeService: NiveauEtudeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClasseUpdateComponent],
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
      .overrideTemplate(ClasseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClasseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    classeService = TestBed.inject(ClasseService);
    etablissementService = TestBed.inject(EtablissementService);
    programmeService = TestBed.inject(ProgrammeService);
    niveauEtudeService = TestBed.inject(NiveauEtudeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Etablissement query and add missing value', () => {
      const classe: IClasse = { id: 456 };
      const etablissement: IEtablissement = { id: 96270 };
      classe.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 3371 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ classe });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Programme query and add missing value', () => {
      const classe: IClasse = { id: 456 };
      const programme: IProgramme = { id: 72090 };
      classe.programme = programme;

      const programmeCollection: IProgramme[] = [{ id: 67487 }];
      jest.spyOn(programmeService, 'query').mockReturnValue(of(new HttpResponse({ body: programmeCollection })));
      const additionalProgrammes = [programme];
      const expectedCollection: IProgramme[] = [...additionalProgrammes, ...programmeCollection];
      jest.spyOn(programmeService, 'addProgrammeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ classe });
      comp.ngOnInit();

      expect(programmeService.query).toHaveBeenCalled();
      expect(programmeService.addProgrammeToCollectionIfMissing).toHaveBeenCalledWith(programmeCollection, ...additionalProgrammes);
      expect(comp.programmesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NiveauEtude query and add missing value', () => {
      const classe: IClasse = { id: 456 };
      const niveauEtude: INiveauEtude = { id: 99168 };
      classe.niveauEtude = niveauEtude;

      const niveauEtudeCollection: INiveauEtude[] = [{ id: 713 }];
      jest.spyOn(niveauEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: niveauEtudeCollection })));
      const additionalNiveauEtudes = [niveauEtude];
      const expectedCollection: INiveauEtude[] = [...additionalNiveauEtudes, ...niveauEtudeCollection];
      jest.spyOn(niveauEtudeService, 'addNiveauEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ classe });
      comp.ngOnInit();

      expect(niveauEtudeService.query).toHaveBeenCalled();
      expect(niveauEtudeService.addNiveauEtudeToCollectionIfMissing).toHaveBeenCalledWith(niveauEtudeCollection, ...additionalNiveauEtudes);
      expect(comp.niveauEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const classe: IClasse = { id: 456 };
      const etablissement: IEtablissement = { id: 37440 };
      classe.etablissement = etablissement;
      const programme: IProgramme = { id: 98423 };
      classe.programme = programme;
      const niveauEtude: INiveauEtude = { id: 13975 };
      classe.niveauEtude = niveauEtude;

      activatedRoute.data = of({ classe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(classe));
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
      expect(comp.programmesSharedCollection).toContain(programme);
      expect(comp.niveauEtudesSharedCollection).toContain(niveauEtude);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Classe>>();
      const classe = { id: 123 };
      jest.spyOn(classeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(classeService.update).toHaveBeenCalledWith(classe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Classe>>();
      const classe = new Classe();
      jest.spyOn(classeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classe }));
      saveSubject.complete();

      // THEN
      expect(classeService.create).toHaveBeenCalledWith(classe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Classe>>();
      const classe = { id: 123 };
      jest.spyOn(classeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(classeService.update).toHaveBeenCalledWith(classe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEtablissementById', () => {
      it('Should return tracked Etablissement primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEtablissementById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProgrammeById', () => {
      it('Should return tracked Programme primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProgrammeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNiveauEtudeById', () => {
      it('Should return tracked NiveauEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNiveauEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
