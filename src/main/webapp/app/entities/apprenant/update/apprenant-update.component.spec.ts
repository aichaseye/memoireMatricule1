import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApprenantService } from '../service/apprenant.service';
import { IApprenant, Apprenant } from '../apprenant.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { NiveauEtudeService } from 'app/entities/niveau-etude/service/niveau-etude.service';
import { IDemandeMatApp } from 'app/entities/demande-mat-app/demande-mat-app.model';
import { DemandeMatAppService } from 'app/entities/demande-mat-app/service/demande-mat-app.service';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';

import { ApprenantUpdateComponent } from './apprenant-update.component';

describe('Apprenant Management Update Component', () => {
  let comp: ApprenantUpdateComponent;
  let fixture: ComponentFixture<ApprenantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apprenantService: ApprenantService;
  let etablissementService: EtablissementService;
  let niveauEtudeService: NiveauEtudeService;
  let demandeMatAppService: DemandeMatAppService;
  let demandeDossierService: DemandeDossierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApprenantUpdateComponent],
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
      .overrideTemplate(ApprenantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApprenantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apprenantService = TestBed.inject(ApprenantService);
    etablissementService = TestBed.inject(EtablissementService);
    niveauEtudeService = TestBed.inject(NiveauEtudeService);
    demandeMatAppService = TestBed.inject(DemandeMatAppService);
    demandeDossierService = TestBed.inject(DemandeDossierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Etablissement query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const etablissement: IEtablissement = { id: 92984 };
      apprenant.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 4021 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NiveauEtude query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const niveauEtude: INiveauEtude = { id: 94298 };
      apprenant.niveauEtude = niveauEtude;

      const niveauEtudeCollection: INiveauEtude[] = [{ id: 57875 }];
      jest.spyOn(niveauEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: niveauEtudeCollection })));
      const additionalNiveauEtudes = [niveauEtude];
      const expectedCollection: INiveauEtude[] = [...additionalNiveauEtudes, ...niveauEtudeCollection];
      jest.spyOn(niveauEtudeService, 'addNiveauEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(niveauEtudeService.query).toHaveBeenCalled();
      expect(niveauEtudeService.addNiveauEtudeToCollectionIfMissing).toHaveBeenCalledWith(niveauEtudeCollection, ...additionalNiveauEtudes);
      expect(comp.niveauEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeMatApp query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const demandeMatApp: IDemandeMatApp = { id: 44276 };
      apprenant.demandeMatApp = demandeMatApp;

      const demandeMatAppCollection: IDemandeMatApp[] = [{ id: 27241 }];
      jest.spyOn(demandeMatAppService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeMatAppCollection })));
      const additionalDemandeMatApps = [demandeMatApp];
      const expectedCollection: IDemandeMatApp[] = [...additionalDemandeMatApps, ...demandeMatAppCollection];
      jest.spyOn(demandeMatAppService, 'addDemandeMatAppToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(demandeMatAppService.query).toHaveBeenCalled();
      expect(demandeMatAppService.addDemandeMatAppToCollectionIfMissing).toHaveBeenCalledWith(
        demandeMatAppCollection,
        ...additionalDemandeMatApps
      );
      expect(comp.demandeMatAppsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeDossier query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const demandeDossier: IDemandeDossier = { id: 64120 };
      apprenant.demandeDossier = demandeDossier;

      const demandeDossierCollection: IDemandeDossier[] = [{ id: 1526 }];
      jest.spyOn(demandeDossierService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDossierCollection })));
      const additionalDemandeDossiers = [demandeDossier];
      const expectedCollection: IDemandeDossier[] = [...additionalDemandeDossiers, ...demandeDossierCollection];
      jest.spyOn(demandeDossierService, 'addDemandeDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(demandeDossierService.query).toHaveBeenCalled();
      expect(demandeDossierService.addDemandeDossierToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDossierCollection,
        ...additionalDemandeDossiers
      );
      expect(comp.demandeDossiersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const apprenant: IApprenant = { id: 456 };
      const etablissement: IEtablissement = { id: 36316 };
      apprenant.etablissement = etablissement;
      const niveauEtude: INiveauEtude = { id: 72805 };
      apprenant.niveauEtude = niveauEtude;
      const demandeMatApp: IDemandeMatApp = { id: 55731 };
      apprenant.demandeMatApp = demandeMatApp;
      const demandeDossier: IDemandeDossier = { id: 50965 };
      apprenant.demandeDossier = demandeDossier;

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(apprenant));
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
      expect(comp.niveauEtudesSharedCollection).toContain(niveauEtude);
      expect(comp.demandeMatAppsSharedCollection).toContain(demandeMatApp);
      expect(comp.demandeDossiersSharedCollection).toContain(demandeDossier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apprenant>>();
      const apprenant = { id: 123 };
      jest.spyOn(apprenantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apprenant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(apprenantService.update).toHaveBeenCalledWith(apprenant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apprenant>>();
      const apprenant = new Apprenant();
      jest.spyOn(apprenantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apprenant }));
      saveSubject.complete();

      // THEN
      expect(apprenantService.create).toHaveBeenCalledWith(apprenant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apprenant>>();
      const apprenant = { id: 123 };
      jest.spyOn(apprenantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apprenantService.update).toHaveBeenCalledWith(apprenant);
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

    describe('trackNiveauEtudeById', () => {
      it('Should return tracked NiveauEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNiveauEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDemandeMatAppById', () => {
      it('Should return tracked DemandeMatApp primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeMatAppById(0, entity);
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
