import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarteScolaire } from '../carte-scolaire.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-carte-scolaire-detail',
  templateUrl: './carte-scolaire-detail.component.html',
})
export class CarteScolaireDetailComponent implements OnInit {
  carteScolaire: ICarteScolaire | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carteScolaire }) => {
      this.carteScolaire = carteScolaire;
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
