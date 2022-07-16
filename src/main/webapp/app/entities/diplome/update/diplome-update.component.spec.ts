import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DiplomeService } from '../service/diplome.service';
import { IDiplome, Diplome } from '../diplome.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { EtatDemandeService } from 'app/entities/etat-demande/service/etat-demande.service';

import { DiplomeUpdateComponent } from './diplome-update.component';

describe('Diplome Management Update Component', () => {
  let comp: DiplomeUpdateComponent;
  let fixture: ComponentFixture<DiplomeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let diplomeService: DiplomeService;
  let etablissementService: EtablissementService;
  let apprenantService: ApprenantService;
  let etatDemandeService: EtatDemandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DiplomeUpdateComponent],
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
      .overrideTemplate(DiplomeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiplomeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    diplomeService = TestBed.inject(DiplomeService);
    etablissementService = TestBed.inject(EtablissementService);
    apprenantService = TestBed.inject(ApprenantService);
    etatDemandeService = TestBed.inject(EtatDemandeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Etablissement query and add missing value', () => {
      const diplome: IDiplome = { id: 456 };
      const etablissement: IEtablissement = { id: 47720 };
      diplome.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 57057 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Apprenant query and add missing value', () => {
      const diplome: IDiplome = { id: 456 };
      const apprenant: IApprenant = { id: 12737 };
      diplome.apprenant = apprenant;

      const apprenantCollection: IApprenant[] = [{ id: 12025 }];
      jest.spyOn(apprenantService, 'query').mockReturnValue(of(new HttpResponse({ body: apprenantCollection })));
      const additionalApprenants = [apprenant];
      const expectedCollection: IApprenant[] = [...additionalApprenants, ...apprenantCollection];
      jest.spyOn(apprenantService, 'addApprenantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(apprenantService.query).toHaveBeenCalled();
      expect(apprenantService.addApprenantToCollectionIfMissing).toHaveBeenCalledWith(apprenantCollection, ...additionalApprenants);
      expect(comp.apprenantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EtatDemande query and add missing value', () => {
      const diplome: IDiplome = { id: 456 };
      const etatDemande: IEtatDemande = { id: 82637 };
      diplome.etatDemande = etatDemande;

      const etatDemandeCollection: IEtatDemande[] = [{ id: 70277 }];
      jest.spyOn(etatDemandeService, 'query').mockReturnValue(of(new HttpResponse({ body: etatDemandeCollection })));
      const additionalEtatDemandes = [etatDemande];
      const expectedCollection: IEtatDemande[] = [...additionalEtatDemandes, ...etatDemandeCollection];
      jest.spyOn(etatDemandeService, 'addEtatDemandeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(etatDemandeService.query).toHaveBeenCalled();
      expect(etatDemandeService.addEtatDemandeToCollectionIfMissing).toHaveBeenCalledWith(etatDemandeCollection, ...additionalEtatDemandes);
      expect(comp.etatDemandesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const diplome: IDiplome = { id: 456 };
      const etablissement: IEtablissement = { id: 35254 };
      diplome.etablissement = etablissement;
      const apprenant: IApprenant = { id: 69202 };
      diplome.apprenant = apprenant;
      const etatDemande: IEtatDemande = { id: 21448 };
      diplome.etatDemande = etatDemande;

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(diplome));
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
      expect(comp.apprenantsSharedCollection).toContain(apprenant);
      expect(comp.etatDemandesSharedCollection).toContain(etatDemande);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Diplome>>();
      const diplome = { id: 123 };
      jest.spyOn(diplomeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diplome }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(diplomeService.update).toHaveBeenCalledWith(diplome);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Diplome>>();
      const diplome = new Diplome();
      jest.spyOn(diplomeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diplome }));
      saveSubject.complete();

      // THEN
      expect(diplomeService.create).toHaveBeenCalledWith(diplome);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Diplome>>();
      const diplome = { id: 123 };
      jest.spyOn(diplomeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(diplomeService.update).toHaveBeenCalledWith(diplome);
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

    describe('trackApprenantById', () => {
      it('Should return tracked Apprenant primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackApprenantById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEtatDemandeById', () => {
      it('Should return tracked EtatDemande primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEtatDemandeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
