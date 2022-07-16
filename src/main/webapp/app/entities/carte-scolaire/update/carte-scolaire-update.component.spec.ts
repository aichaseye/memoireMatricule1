import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CarteScolaireService } from '../service/carte-scolaire.service';
import { ICarteScolaire, CarteScolaire } from '../carte-scolaire.model';
import { IDemandeDossier } from 'app/entities/demande-dossier/demande-dossier.model';
import { DemandeDossierService } from 'app/entities/demande-dossier/service/demande-dossier.service';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';

import { CarteScolaireUpdateComponent } from './carte-scolaire-update.component';

describe('CarteScolaire Management Update Component', () => {
  let comp: CarteScolaireUpdateComponent;
  let fixture: ComponentFixture<CarteScolaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carteScolaireService: CarteScolaireService;
  let demandeDossierService: DemandeDossierService;
  let apprenantService: ApprenantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CarteScolaireUpdateComponent],
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
      .overrideTemplate(CarteScolaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarteScolaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    carteScolaireService = TestBed.inject(CarteScolaireService);
    demandeDossierService = TestBed.inject(DemandeDossierService);
    apprenantService = TestBed.inject(ApprenantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DemandeDossier query and add missing value', () => {
      const carteScolaire: ICarteScolaire = { id: 456 };
      const demandeDossier: IDemandeDossier = { id: 4009 };
      carteScolaire.demandeDossier = demandeDossier;

      const demandeDossierCollection: IDemandeDossier[] = [{ id: 48111 }];
      jest.spyOn(demandeDossierService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDossierCollection })));
      const additionalDemandeDossiers = [demandeDossier];
      const expectedCollection: IDemandeDossier[] = [...additionalDemandeDossiers, ...demandeDossierCollection];
      jest.spyOn(demandeDossierService, 'addDemandeDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carteScolaire });
      comp.ngOnInit();

      expect(demandeDossierService.query).toHaveBeenCalled();
      expect(demandeDossierService.addDemandeDossierToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDossierCollection,
        ...additionalDemandeDossiers
      );
      expect(comp.demandeDossiersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Apprenant query and add missing value', () => {
      const carteScolaire: ICarteScolaire = { id: 456 };
      const apprenant: IApprenant = { id: 48572 };
      carteScolaire.apprenant = apprenant;

      const apprenantCollection: IApprenant[] = [{ id: 17195 }];
      jest.spyOn(apprenantService, 'query').mockReturnValue(of(new HttpResponse({ body: apprenantCollection })));
      const additionalApprenants = [apprenant];
      const expectedCollection: IApprenant[] = [...additionalApprenants, ...apprenantCollection];
      jest.spyOn(apprenantService, 'addApprenantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carteScolaire });
      comp.ngOnInit();

      expect(apprenantService.query).toHaveBeenCalled();
      expect(apprenantService.addApprenantToCollectionIfMissing).toHaveBeenCalledWith(apprenantCollection, ...additionalApprenants);
      expect(comp.apprenantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const carteScolaire: ICarteScolaire = { id: 456 };
      const demandeDossier: IDemandeDossier = { id: 99411 };
      carteScolaire.demandeDossier = demandeDossier;
      const apprenant: IApprenant = { id: 13630 };
      carteScolaire.apprenant = apprenant;

      activatedRoute.data = of({ carteScolaire });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(carteScolaire));
      expect(comp.demandeDossiersSharedCollection).toContain(demandeDossier);
      expect(comp.apprenantsSharedCollection).toContain(apprenant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CarteScolaire>>();
      const carteScolaire = { id: 123 };
      jest.spyOn(carteScolaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carteScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carteScolaire }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(carteScolaireService.update).toHaveBeenCalledWith(carteScolaire);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CarteScolaire>>();
      const carteScolaire = new CarteScolaire();
      jest.spyOn(carteScolaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carteScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carteScolaire }));
      saveSubject.complete();

      // THEN
      expect(carteScolaireService.create).toHaveBeenCalledWith(carteScolaire);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CarteScolaire>>();
      const carteScolaire = { id: 123 };
      jest.spyOn(carteScolaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carteScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(carteScolaireService.update).toHaveBeenCalledWith(carteScolaire);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDemandeDossierById', () => {
      it('Should return tracked DemandeDossier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeDossierById(0, entity);
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
  });
});
