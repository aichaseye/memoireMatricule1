import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EtatDemandeDetailComponent } from './etat-demande-detail.component';

describe('EtatDemande Management Detail Component', () => {
  let comp: EtatDemandeDetailComponent;
  let fixture: ComponentFixture<EtatDemandeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EtatDemandeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ etatDemande: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EtatDemandeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EtatDemandeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load etatDemande on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.etatDemande).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
