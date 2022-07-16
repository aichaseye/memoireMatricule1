import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatDemande } from '../etat-demande.model';

@Component({
  selector: 'jhi-etat-demande-detail',
  templateUrl: './etat-demande-detail.component.html',
})
export class EtatDemandeDetailComponent implements OnInit {
  etatDemande: IEtatDemande | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatDemande }) => {
      this.etatDemande = etatDemande;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
