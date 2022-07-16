import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AttestationService } from '../service/attestation.service';
import { IAttestation, Attestation } from '../attestation.model';
import { IEtatDemande } from 'app/entities/etat-demande/etat-demande.model';
import { EtatDemandeService } from 'app/entities/etat-demande/service/etat-demande.service';

import { AttestationUpdateComponent } from './attestation-update.component';

describe('Attestation Management Update Component', () => {
  let comp: AttestationUpdateComponent;
  let fixture: ComponentFixture<AttestationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let attestationService: AttestationService;
  let etatDemandeService: EtatDemandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AttestationUpdateComponent],
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
      .overrideTemplate(AttestationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AttestationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    attestationService = TestBed.inject(AttestationService);
    etatDemandeService = TestBed.inject(EtatDemandeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EtatDemande query and add missing value', () => {
      const attestation: IAttestation = { id: 456 };
      const etatDemande: IEtatDemande = { id: 17851 };
      attestation.etatDemande = etatDemande;

      const etatDemandeCollection: IEtatDemande[] = [{ id: 96419 }];
      jest.spyOn(etatDemandeService, 'query').mockReturnValue(of(new HttpResponse({ body: etatDemandeCollection })));
      const additionalEtatDemandes = [etatDemande];
      const expectedCollection: IEtatDemande[] = [...additionalEtatDemandes, ...etatDemandeCollection];
      jest.spyOn(etatDemandeService, 'addEtatDemandeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ attestation });
      comp.ngOnInit();

      expect(etatDemandeService.query).toHaveBeenCalled();
      expect(etatDemandeService.addEtatDemandeToCollectionIfMissing).toHaveBeenCalledWith(etatDemandeCollection, ...additionalEtatDemandes);
      expect(comp.etatDemandesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const attestation: IAttestation = { id: 456 };
      const etatDemande: IEtatDemande = { id: 14501 };
      attestation.etatDemande = etatDemande;

      activatedRoute.data = of({ attestation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(attestation));
      expect(comp.etatDemandesSharedCollection).toContain(etatDemande);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Attestation>>();
      const attestation = { id: 123 };
      jest.spyOn(attestationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attestation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attestation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(attestationService.update).toHaveBeenCalledWith(attestation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Attestation>>();
      const attestation = new Attestation();
      jest.spyOn(attestationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attestation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attestation }));
      saveSubject.complete();

      // THEN
      expect(attestationService.create).toHaveBeenCalledWith(attestation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Attestation>>();
      const attestation = { id: 123 };
      jest.spyOn(attestationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attestation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(attestationService.update).toHaveBeenCalledWith(attestation);
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
