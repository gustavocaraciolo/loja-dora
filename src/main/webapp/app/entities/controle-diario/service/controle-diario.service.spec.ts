import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IControleDiario } from '../controle-diario.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../controle-diario.test-samples';

import { ControleDiarioService, RestControleDiario } from './controle-diario.service';

const requireRestSample: RestControleDiario = {
  ...sampleWithRequiredData,
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
};

describe('ControleDiario Service', () => {
  let service: ControleDiarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IControleDiario | IControleDiario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ControleDiarioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ControleDiario', () => {
      const controleDiario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(controleDiario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ControleDiario', () => {
      const controleDiario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(controleDiario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ControleDiario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ControleDiario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ControleDiario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addControleDiarioToCollectionIfMissing', () => {
      it('should add a ControleDiario to an empty array', () => {
        const controleDiario: IControleDiario = sampleWithRequiredData;
        expectedResult = service.addControleDiarioToCollectionIfMissing([], controleDiario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleDiario);
      });

      it('should not add a ControleDiario to an array that contains it', () => {
        const controleDiario: IControleDiario = sampleWithRequiredData;
        const controleDiarioCollection: IControleDiario[] = [
          {
            ...controleDiario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addControleDiarioToCollectionIfMissing(controleDiarioCollection, controleDiario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ControleDiario to an array that doesn't contain it", () => {
        const controleDiario: IControleDiario = sampleWithRequiredData;
        const controleDiarioCollection: IControleDiario[] = [sampleWithPartialData];
        expectedResult = service.addControleDiarioToCollectionIfMissing(controleDiarioCollection, controleDiario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleDiario);
      });

      it('should add only unique ControleDiario to an array', () => {
        const controleDiarioArray: IControleDiario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const controleDiarioCollection: IControleDiario[] = [sampleWithRequiredData];
        expectedResult = service.addControleDiarioToCollectionIfMissing(controleDiarioCollection, ...controleDiarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const controleDiario: IControleDiario = sampleWithRequiredData;
        const controleDiario2: IControleDiario = sampleWithPartialData;
        expectedResult = service.addControleDiarioToCollectionIfMissing([], controleDiario, controleDiario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleDiario);
        expect(expectedResult).toContain(controleDiario2);
      });

      it('should accept null and undefined values', () => {
        const controleDiario: IControleDiario = sampleWithRequiredData;
        expectedResult = service.addControleDiarioToCollectionIfMissing([], null, controleDiario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleDiario);
      });

      it('should return initial array if no ControleDiario is added', () => {
        const controleDiarioCollection: IControleDiario[] = [sampleWithRequiredData];
        expectedResult = service.addControleDiarioToCollectionIfMissing(controleDiarioCollection, undefined, null);
        expect(expectedResult).toEqual(controleDiarioCollection);
      });
    });

    describe('compareControleDiario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareControleDiario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareControleDiario(entity1, entity2);
        const compareResult2 = service.compareControleDiario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareControleDiario(entity1, entity2);
        const compareResult2 = service.compareControleDiario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareControleDiario(entity1, entity2);
        const compareResult2 = service.compareControleDiario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
