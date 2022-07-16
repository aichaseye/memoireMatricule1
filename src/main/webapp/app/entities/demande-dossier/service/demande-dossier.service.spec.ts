import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { TypeDossier } from 'app/entities/enumerations/type-dossier.model';
import { Serie } from 'app/entities/enumerations/serie.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';
import { NomSemestre } from 'app/entities/enumerations/nom-semestre.model';
import { Niveau } from 'app/entities/enumerations/niveau.model';
import { TypeReleve } from 'app/entities/enumerations/type-releve.model';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';
import { IDemandeDossier, DemandeDossier } from '../demande-dossier.model';

import { DemandeDossierService } from './demande-dossier.service';

describe('DemandeDossier Service', () => {
  let service: DemandeDossierService;
  let httpMock: HttpTestingController;
  let elemDefault: IDemandeDossier;
  let expectedResult: IDemandeDossier | IDemandeDossier[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeDossierService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nom: 'AAAAAAA',
      prenom: 'AAAAAAA',
      email: 'AAAAAAA',
      dateNaissance: currentDate,
      matricule: 'AAAAAAA',
      typeDossier: TypeDossier.Diplome,
      annee: currentDate,
      serie: Serie.STEG,
      filiere: Filiere.Agricultre,
      nomSemestre: NomSemestre.Semestre1,
      niveau: Niveau.CAP1,
      typeReleve: TypeReleve.Admis,
      nomDiplome: NomDiplome.CAP,
      photoContentType: 'image/png',
      photo: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateNaissance: currentDate.format(DATE_FORMAT),
          annee: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DemandeDossier', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateNaissance: currentDate.format(DATE_FORMAT),
          annee: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
          annee: currentDate,
        },
        returnedFromService
      );

      service.create(new DemandeDossier()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemandeDossier', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          email: 'BBBBBB',
          dateNaissance: currentDate.format(DATE_FORMAT),
          matricule: 'BBBBBB',
          typeDossier: 'BBBBBB',
          annee: currentDate.format(DATE_FORMAT),
          serie: 'BBBBBB',
          filiere: 'BBBBBB',
          nomSemestre: 'BBBBBB',
          niveau: 'BBBBBB',
          typeReleve: 'BBBBBB',
          nomDiplome: 'BBBBBB',
          photo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
          annee: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemandeDossier', () => {
      const patchObject = Object.assign(
        {
          nom: 'BBBBBB',
          typeDossier: 'BBBBBB',
          annee: currentDate.format(DATE_FORMAT),
          typeReleve: 'BBBBBB',
          nomDiplome: 'BBBBBB',
          photo: 'BBBBBB',
        },
        new DemandeDossier()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
          annee: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemandeDossier', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          email: 'BBBBBB',
          dateNaissance: currentDate.format(DATE_FORMAT),
          matricule: 'BBBBBB',
          typeDossier: 'BBBBBB',
          annee: currentDate.format(DATE_FORMAT),
          serie: 'BBBBBB',
          filiere: 'BBBBBB',
          nomSemestre: 'BBBBBB',
          niveau: 'BBBBBB',
          typeReleve: 'BBBBBB',
          nomDiplome: 'BBBBBB',
          photo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
          annee: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DemandeDossier', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDemandeDossierToCollectionIfMissing', () => {
      it('should add a DemandeDossier to an empty array', () => {
        const demandeDossier: IDemandeDossier = { id: 123 };
        expectedResult = service.addDemandeDossierToCollectionIfMissing([], demandeDossier);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeDossier);
      });

      it('should not add a DemandeDossier to an array that contains it', () => {
        const demandeDossier: IDemandeDossier = { id: 123 };
        const demandeDossierCollection: IDemandeDossier[] = [
          {
            ...demandeDossier,
          },
          { id: 456 },
        ];
        expectedResult = service.addDemandeDossierToCollectionIfMissing(demandeDossierCollection, demandeDossier);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemandeDossier to an array that doesn't contain it", () => {
        const demandeDossier: IDemandeDossier = { id: 123 };
        const demandeDossierCollection: IDemandeDossier[] = [{ id: 456 }];
        expectedResult = service.addDemandeDossierToCollectionIfMissing(demandeDossierCollection, demandeDossier);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeDossier);
      });

      it('should add only unique DemandeDossier to an array', () => {
        const demandeDossierArray: IDemandeDossier[] = [{ id: 123 }, { id: 456 }, { id: 97952 }];
        const demandeDossierCollection: IDemandeDossier[] = [{ id: 123 }];
        expectedResult = service.addDemandeDossierToCollectionIfMissing(demandeDossierCollection, ...demandeDossierArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeDossier: IDemandeDossier = { id: 123 };
        const demandeDossier2: IDemandeDossier = { id: 456 };
        expectedResult = service.addDemandeDossierToCollectionIfMissing([], demandeDossier, demandeDossier2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeDossier);
        expect(expectedResult).toContain(demandeDossier2);
      });

      it('should accept null and undefined values', () => {
        const demandeDossier: IDemandeDossier = { id: 123 };
        expectedResult = service.addDemandeDossierToCollectionIfMissing([], null, demandeDossier, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeDossier);
      });

      it('should return initial array if no DemandeDossier is added', () => {
        const demandeDossierCollection: IDemandeDossier[] = [{ id: 123 }];
        expectedResult = service.addDemandeDossierToCollectionIfMissing(demandeDossierCollection, undefined, null);
        expect(expectedResult).toEqual(demandeDossierCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
