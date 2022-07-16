import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObservationDetailComponent } from './observation-detail.component';

describe('Observation Management Detail Component', () => {
  let comp: ObservationDetailComponent;
  let fixture: ComponentFixture<ObservationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ObservationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ observation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ObservationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ObservationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load observation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.observation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
