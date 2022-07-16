import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAttestation, Attestation } from '../attestation.model';

import { AttestationService } from './attestation.service';

describe('Attestation Service', () => {
  let service: AttestationService;
  let httpMock: HttpTestingController;
  let elemDefault: IAttestation;
  let expectedResult: IAttestation | IAttestation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AttestationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      photoContentType: 'image/png',
      photo: 'AAAAAAA',
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

    it('should create a Attestation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Attestation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Attestation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          photo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Attestation', () => {
      const patchObject = Object.assign({}, new Attestation());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Attestation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          photo: 'BBBBBB',
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

    it('should delete a Attestation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAttestationToCollectionIfMissing', () => {
      it('should add a Attestation to an empty array', () => {
        const attestation: IAttestation = { id: 123 };
        expectedResult = service.addAttestationToCollectionIfMissing([], attestation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attestation);
      });

      it('should not add a Attestation to an array that contains it', () => {
        const attestation: IAttestation = { id: 123 };
        const attestationCollection: IAttestation[] = [
          {
            ...attestation,
          },
          { id: 456 },
        ];
        expectedResult = service.addAttestationToCollectionIfMissing(attestationCollection, attestation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Attestation to an array that doesn't contain it", () => {
        const attestation: IAttestation = { id: 123 };
        const attestationCollection: IAttestation[] = [{ id: 456 }];
        expectedResult = service.addAttestationToCollectionIfMissing(attestationCollection, attestation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attestation);
      });

      it('should add only unique Attestation to an array', () => {
        const attestationArray: IAttestation[] = [{ id: 123 }, { id: 456 }, { id: 19586 }];
        const attestationCollection: IAttestation[] = [{ id: 123 }];
        expectedResult = service.addAttestationToCollectionIfMissing(attestationCollection, ...attestationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const attestation: IAttestation = { id: 123 };
        const attestation2: IAttestation = { id: 456 };
        expectedResult = service.addAttestationToCollectionIfMissing([], attestation, attestation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attestation);
        expect(expectedResult).toContain(attestation2);
      });

      it('should accept null and undefined values', () => {
        const attestation: IAttestation = { id: 123 };
        expectedResult = service.addAttestationToCollectionIfMissing([], null, attestation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attestation);
      });

      it('should return initial array if no Attestation is added', () => {
        const attestationCollection: IAttestation[] = [{ id: 123 }];
        expectedResult = service.addAttestationToCollectionIfMissing(attestationCollection, undefined, null);
        expect(expectedResult).toEqual(attestationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
