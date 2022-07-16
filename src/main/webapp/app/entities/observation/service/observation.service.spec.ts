import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Asuduite } from 'app/entities/enumerations/asuduite.model';
import { Ponctualite } from 'app/entities/enumerations/ponctualite.model';
import { Apte } from 'app/entities/enumerations/apte.model';
import { IObservation, Observation } from '../observation.model';

import { ObservationService } from './observation.service';

describe('Observation Service', () => {
  let service: ObservationService;
  let httpMock: HttpTestingController;
  let elemDefault: IObservation;
  let expectedResult: IObservation | IObservation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ObservationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      asuduite: Asuduite.OUI,
      ponctualite: Ponctualite.OUI,
      apte: Apte.OUI,
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

    it('should create a Observation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Observation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Observation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          asuduite: 'BBBBBB',
          ponctualite: 'BBBBBB',
          apte: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Observation', () => {
      const patchObject = Object.assign({}, new Observation());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Observation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          asuduite: 'BBBBBB',
          ponctualite: 'BBBBBB',
          apte: 'BBBBBB',
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

    it('should delete a Observation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addObservationToCollectionIfMissing', () => {
      it('should add a Observation to an empty array', () => {
        const observation: IObservation = { id: 123 };
        expectedResult = service.addObservationToCollectionIfMissing([], observation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(observation);
      });

      it('should not add a Observation to an array that contains it', () => {
        const observation: IObservation = { id: 123 };
        const observationCollection: IObservation[] = [
          {
            ...observation,
          },
          { id: 456 },
        ];
        expectedResult = service.addObservationToCollectionIfMissing(observationCollection, observation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Observation to an array that doesn't contain it", () => {
        const observation: IObservation = { id: 123 };
        const observationCollection: IObservation[] = [{ id: 456 }];
        expectedResult = service.addObservationToCollectionIfMissing(observationCollection, observation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(observation);
      });

      it('should add only unique Observation to an array', () => {
        const observationArray: IObservation[] = [{ id: 123 }, { id: 456 }, { id: 99117 }];
        const observationCollection: IObservation[] = [{ id: 123 }];
        expectedResult = service.addObservationToCollectionIfMissing(observationCollection, ...observationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const observation: IObservation = { id: 123 };
        const observation2: IObservation = { id: 456 };
        expectedResult = service.addObservationToCollectionIfMissing([], observation, observation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(observation);
        expect(expectedResult).toContain(observation2);
      });

      it('should accept null and undefined values', () => {
        const observation: IObservation = { id: 123 };
        expectedResult = service.addObservationToCollectionIfMissing([], null, observation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(observation);
      });

      it('should return initial array if no Observation is added', () => {
        const observationCollection: IObservation[] = [{ id: 123 }];
        expectedResult = service.addObservationToCollectionIfMissing(observationCollection, undefined, null);
        expect(expectedResult).toEqual(observationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
