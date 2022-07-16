import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObservation } from '../observation.model';

@Component({
  selector: 'jhi-observation-detail',
  templateUrl: './observation-detail.component.html',
})
export class ObservationDetailComponent implements OnInit {
  observation: IObservation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observation }) => {
      this.observation = observation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
