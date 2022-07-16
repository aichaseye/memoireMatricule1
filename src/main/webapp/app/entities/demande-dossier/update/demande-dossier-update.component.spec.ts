import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemandeDossierService } from '../service/demande-dossier.service';
import { IDemandeDossier, DemandeDossier } from '../demande-dossier.model';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { EtatDemandeService } from 'app/entities/etat-demande/service/etat-demande.service';

import { DemandeDossierUpdateComponent } from './demande-dossier-update.component';

describe('DemandeDossier Management Update Component', () => {
  let comp: DemandeDossierUpdateComponent;
  let fixture: ComponentFixture<DemandeDossierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeDossierService: DemandeDossierService;
  let etatDemandeService: EtatDemandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemandeDossierUpdateComponent],
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
      .overrideTemplate(DemandeDossierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeDossierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeDossierService = TestBed.inject(DemandeDossierService);
    etatDemandeService = TestBed.inject(EtatDemandeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EtatDemande query and add missing value', () => {
      const demandeDossier: IDemandeDossier = { id: 456 };
      const etatDemande: IEtatDemande = { id: 63881 };
      demandeDossier.etatDemande = etatDemande;

      const etatDemandeCollection: IEtatDemande[] = [{ id: 40477 }];
      jest.spyOn(etatDemandeService, 'query').mockReturnValue(of(new HttpResponse({ body: etatDemandeCollection })));
      const additionalEtatDemandes = [etatDemande];
      const expectedCollection: IEtatDemande[] = [...additionalEtatDemandes, ...etatDemandeCollection];
      jest.spyOn(etatDemandeService, 'addEtatDemandeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeDossier });
      comp.ngOnInit();

      expect(etatDemandeService.query).toHaveBeenCalled();
      expect(etatDemandeService.addEtatDemandeToCollectionIfMissing).toHaveBeenCalledWith(etatDemandeCollection, ...additionalEtatDemandes);
      expect(comp.etatDemandesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const demandeDossier: IDemandeDossier = { id: 456 };
      const etatDemande: IEtatDemande = { id: 31352 };
      demandeDossier.etatDemande = etatDemande;

      activatedRoute.data = of({ demandeDossier });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(demandeDossier));
      expect(comp.etatDemandesSharedCollection).toContain(etatDemande);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeDossier>>();
      const demandeDossier = { id: 123 };
      jest.spyOn(demandeDossierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeDossier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeDossier }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeDossierService.update).toHaveBeenCalledWith(demandeDossier);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeDossier>>();
      const demandeDossier = new DemandeDossier();
      jest.spyOn(demandeDossierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeDossier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeDossier }));
      saveSubject.complete();

      // THEN
      expect(demandeDossierService.create).toHaveBeenCalledWith(demandeDossier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeDossier>>();
      const demandeDossier = { id: 123 };
      jest.spyOn(demandeDossierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeDossier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeDossierService.update).toHaveBeenCalledWith(demandeDossier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEtatDemandeById', () => {
      it('Should return tracked EtatDemande primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEtatDemandeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
