import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeDossier } from '../demande-dossier.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-demande-dossier-detail',
  templateUrl: './demande-dossier-detail.component.html',
})
export class DemandeDossierDetailComponent implements OnInit {
  demandeDossier: IDemandeDossier | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeDossier }) => {
      this.demandeDossier = demandeDossier;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
