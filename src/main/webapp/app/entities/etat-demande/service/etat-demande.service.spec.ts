import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TypeDossier } from 'app/entities/enumerations/type-dossier.model';
import { Status } from 'app/entities/enumerations/status.model';
import { IEtatDemande, EtatDemande } from '../etat-demande.model';

import { EtatDemandeService } from './etat-demande.service';

describe('EtatDemande Service', () => {
  let service: EtatDemandeService;
  let httpMock: HttpTestingController;
  let elemDefault: IEtatDemande;
  let expectedResult: IEtatDemande | IEtatDemande[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EtatDemandeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      matricule: 'AAAAAAA',
      typeDossier: TypeDossier.Diplome,
      status: Status.Disponible,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a EtatDemande', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EtatDemande()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EtatDemande', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          matricule: 'BBBBBB',
          typeDossier: 'BBBBBB',
          status: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EtatDemande', () => {
      const patchObject = Object.assign(
        {
          matricule: 'BBBBBB',
          status: 'BBBBBB',
        },
        new EtatDemande()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EtatDemande', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          matricule: 'BBBBBB',
          typeDossier: 'BBBBBB',
          status: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a EtatDemande', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEtatDemandeToCollectionIfMissing', () => {
      it('should add a EtatDemande to an empty array', () => {
        const etatDemande: IEtatDemande = { id: 123 };
        expectedResult = service.addEtatDemandeToCollectionIfMissing([], etatDemande);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(etatDemande);
      });

      it('should not add a EtatDemande to an array that contains it', () => {
        const etatDemande: IEtatDemande = { id: 123 };
        const etatDemandeCollection: IEtatDemande[] = [
          {
            ...etatDemande,
          },
          { id: 456 },
        ];
        expectedResult = service.addEtatDemandeToCollectionIfMissing(etatDemandeCollection, etatDemande);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EtatDemande to an array that doesn't contain it", () => {
        const etatDemande: IEtatDemande = { id: 123 };
        const etatDemandeCollection: IEtatDemande[] = [{ id: 456 }];
        expectedResult = service.addEtatDemandeToCollectionIfMissing(etatDemandeCollection, etatDemande);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(etatDemande);
      });

      it('should add only unique EtatDemande to an array', () => {
        const etatDemandeArray: IEtatDemande[] = [{ id: 123 }, { id: 456 }, { id: 31037 }];
        const etatDemandeCollection: IEtatDemande[] = [{ id: 123 }];
        expectedResult = service.addEtatDemandeToCollectionIfMissing(etatDemandeCollection, ...etatDemandeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const etatDemande: IEtatDemande = { id: 123 };
        const etatDemande2: IEtatDemande = { id: 456 };
        expectedResult = service.addEtatDemandeToCollectionIfMissing([], etatDemande, etatDemande2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(etatDemande);
        expect(expectedResult).toContain(etatDemande2);
      });

      it('should accept null and undefined values', () => {
        const etatDemande: IEtatDemande = { id: 123 };
        expectedResult = service.addEtatDemandeToCollectionIfMissing([], null, etatDemande, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(etatDemande);
      });

      it('should return initial array if no EtatDemande is added', () => {
        const etatDemandeCollection: IEtatDemande[] = [{ id: 123 }];
        expectedResult = service.addEtatDemandeToCollectionIfMissing(etatDemandeCollection, undefined, null);
        expect(expectedResult).toEqual(etatDemandeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
