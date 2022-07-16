import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ObservationService } from '../service/observation.service';
import { IObservation, Observation } from '../observation.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { ApprenantService } from 'app/entities/apprenant/service/apprenant.service';

import { ObservationUpdateComponent } from './observation-update.component';

describe('Observation Management Update Component', () => {
  let comp: ObservationUpdateComponent;
  let fixture: ComponentFixture<ObservationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let observationService: ObservationService;
  let apprenantService: ApprenantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ObservationUpdateComponent],
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
      .overrideTemplate(ObservationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ObservationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    observationService = TestBed.inject(ObservationService);
    apprenantService = TestBed.inject(ApprenantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Apprenant query and add missing value', () => {
      const observation: IObservation = { id: 456 };
      const apprenant: IApprenant = { id: 44085 };
      observation.apprenant = apprenant;

      const apprenantCollection: IApprenant[] = [{ id: 13079 }];
      jest.spyOn(apprenantService, 'query').mockReturnValue(of(new HttpResponse({ body: apprenantCollection })));
      const additionalApprenants = [apprenant];
      const expectedCollection: IApprenant[] = [...additionalApprenants, ...apprenantCollection];
      jest.spyOn(apprenantService, 'addApprenantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ observation });
      comp.ngOnInit();

      expect(apprenantService.query).toHaveBeenCalled();
      expect(apprenantService.addApprenantToCollectionIfMissing).toHaveBeenCalledWith(apprenantCollection, ...additionalApprenants);
      expect(comp.apprenantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const observation: IObservation = { id: 456 };
      const apprenant: IApprenant = { id: 11359 };
      observation.apprenant = apprenant;

      activatedRoute.data = of({ observation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(observation));
      expect(comp.apprenantsSharedCollection).toContain(apprenant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Observation>>();
      const observation = { id: 123 };
      jest.spyOn(observationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: observation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(observationService.update).toHaveBeenCalledWith(observation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Observation>>();
      const observation = new Observation();
      jest.spyOn(observationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: observation }));
      saveSubject.complete();

      // THEN
      expect(observationService.create).toHaveBeenCalledWith(observation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Observation>>();
      const observation = { id: 123 };
      jest.spyOn(observationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(observationService.update).toHaveBeenCalledWith(observation);
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
  });
});
