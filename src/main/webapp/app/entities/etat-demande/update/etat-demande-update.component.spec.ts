import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EtatDemandeService } from '../service/etat-demande.service';
import { IEtatDemande, EtatDemande } from '../etat-demande.model';

import { EtatDemandeUpdateComponent } from './etat-demande-update.component';

describe('EtatDemande Management Update Component', () => {
  let comp: EtatDemandeUpdateComponent;
  let fixture: ComponentFixture<EtatDemandeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let etatDemandeService: EtatDemandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EtatDemandeUpdateComponent],
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
      .overrideTemplate(EtatDemandeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EtatDemandeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    etatDemandeService = TestBed.inject(EtatDemandeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const etatDemande: IEtatDemande = { id: 456 };

      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(etatDemande));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EtatDemande>>();
      const etatDemande = { id: 123 };
      jest.spyOn(etatDemandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etatDemande }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(etatDemandeService.update).toHaveBeenCalledWith(etatDemande);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EtatDemande>>();
      const etatDemande = new EtatDemande();
      jest.spyOn(etatDemandeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etatDemande }));
      saveSubject.complete();

      // THEN
      expect(etatDemandeService.create).toHaveBeenCalledWith(etatDemande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EtatDemande>>();
      const etatDemande = { id: 123 };
      jest.spyOn(etatDemandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(etatDemandeService.update).toHaveBeenCalledWith(etatDemande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
