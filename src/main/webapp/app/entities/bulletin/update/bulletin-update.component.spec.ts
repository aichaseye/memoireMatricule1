import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BulletinService } from '../service/bulletin.service';
import { IBulletin, Bulletin } from '../bulletin.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';

import { BulletinUpdateComponent } from './bulletin-update.component';

describe('Bulletin Management Update Component', () => {
  let comp: BulletinUpdateComponent;
  let fixture: ComponentFixture<BulletinUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bulletinService: BulletinService;
  let apprenantService: ApprenantService;
  let demandeDossierService: DemandeDossierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BulletinUpdateComponent],
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
      .overrideTemplate(BulletinUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BulletinUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bulletinService = TestBed.inject(BulletinService);
    apprenantService = TestBed.inject(ApprenantService);
    demandeDossierService = TestBed.inject(DemandeDossierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Apprenant query and add missing value', () => {
      const bulletin: IBulletin = { id: 456 };
      const apprenant: IApprenant = { id: 48623 };
      bulletin.apprenant = apprenant;

      const apprenantCollection: IApprenant[] = [{ id: 92857 }];
      jest.spyOn(apprenantService, 'query').mockReturnValue(of(new HttpResponse({ body: apprenantCollection })));
      const additionalApprenants = [apprenant];
      const expectedCollection: IApprenant[] = [...additionalApprenants, ...apprenantCollection];
      jest.spyOn(apprenantService, 'addApprenantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bulletin });
      comp.ngOnInit();

      expect(apprenantService.query).toHaveBeenCalled();
      expect(apprenantService.addApprenantToCollectionIfMissing).toHaveBeenCalledWith(apprenantCollection, ...additionalApprenants);
      expect(comp.apprenantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeDossier query and add missing value', () => {
      const bulletin: IBulletin = { id: 456 };
      const demandeDossier: IDemandeDossier = { id: 31019 };
      bulletin.demandeDossier = demandeDossier;

      const demandeDossierCollection: IDemandeDossier[] = [{ id: 72497 }];
      jest.spyOn(demandeDossierService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDossierCollection })));
      const additionalDemandeDossiers = [demandeDossier];
      const expectedCollection: IDemandeDossier[] = [...additionalDemandeDossiers, ...demandeDossierCollection];
      jest.spyOn(demandeDossierService, 'addDemandeDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bulletin });
      comp.ngOnInit();

      expect(demandeDossierService.query).toHaveBeenCalled();
      expect(demandeDossierService.addDemandeDossierToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDossierCollection,
        ...additionalDemandeDossiers
      );
      expect(comp.demandeDossiersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bulletin: IBulletin = { id: 456 };
      const apprenant: IApprenant = { id: 22109 };
      bulletin.apprenant = apprenant;
      const demandeDossier: IDemandeDossier = { id: 63432 };
      bulletin.demandeDossier = demandeDossier;

      activatedRoute.data = of({ bulletin });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bulletin));
      expect(comp.apprenantsSharedCollection).toContain(apprenant);
      expect(comp.demandeDossiersSharedCollection).toContain(demandeDossier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bulletin>>();
      const bulletin = { id: 123 };
      jest.spyOn(bulletinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bulletin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bulletin }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bulletinService.update).toHaveBeenCalledWith(bulletin);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bulletin>>();
      const bulletin = new Bulletin();
      jest.spyOn(bulletinService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bulletin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bulletin }));
      saveSubject.complete();

      // THEN
      expect(bulletinService.create).toHaveBeenCalledWith(bulletin);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bulletin>>();
      const bulletin = { id: 123 };
      jest.spyOn(bulletinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bulletin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bulletinService.update).toHaveBeenCalledWith(bulletin);
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

    describe('trackDemandeDossierById', () => {
      it('Should return tracked DemandeDossier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeDossierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
