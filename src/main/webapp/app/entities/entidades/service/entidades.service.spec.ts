import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEntidades } from '../entidades.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../entidades.test-samples';

import { EntidadesService } from './entidades.service';

const requireRestSample: IEntidades = {
  ...sampleWithRequiredData,
};

describe('Entidades Service', () => {
  let service: EntidadesService;
  let httpMock: HttpTestingController;
  let expectedResult: IEntidades | IEntidades[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EntidadesService);
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

    it('should create a Entidades', () => {
      const entidades = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(entidades).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Entidades', () => {
      const entidades = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(entidades).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Entidades', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Entidades', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Entidades', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEntidadesToCollectionIfMissing', () => {
      it('should add a Entidades to an empty array', () => {
        const entidades: IEntidades = sampleWithRequiredData;
        expectedResult = service.addEntidadesToCollectionIfMissing([], entidades);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entidades);
      });

      it('should not add a Entidades to an array that contains it', () => {
        const entidades: IEntidades = sampleWithRequiredData;
        const entidadesCollection: IEntidades[] = [
          {
            ...entidades,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEntidadesToCollectionIfMissing(entidadesCollection, entidades);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Entidades to an array that doesn't contain it", () => {
        const entidades: IEntidades = sampleWithRequiredData;
        const entidadesCollection: IEntidades[] = [sampleWithPartialData];
        expectedResult = service.addEntidadesToCollectionIfMissing(entidadesCollection, entidades);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entidades);
      });

      it('should add only unique Entidades to an array', () => {
        const entidadesArray: IEntidades[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const entidadesCollection: IEntidades[] = [sampleWithRequiredData];
        expectedResult = service.addEntidadesToCollectionIfMissing(entidadesCollection, ...entidadesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const entidades: IEntidades = sampleWithRequiredData;
        const entidades2: IEntidades = sampleWithPartialData;
        expectedResult = service.addEntidadesToCollectionIfMissing([], entidades, entidades2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entidades);
        expect(expectedResult).toContain(entidades2);
      });

      it('should accept null and undefined values', () => {
        const entidades: IEntidades = sampleWithRequiredData;
        expectedResult = service.addEntidadesToCollectionIfMissing([], null, entidades, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entidades);
      });

      it('should return initial array if no Entidades is added', () => {
        const entidadesCollection: IEntidades[] = [sampleWithRequiredData];
        expectedResult = service.addEntidadesToCollectionIfMissing(entidadesCollection, undefined, null);
        expect(expectedResult).toEqual(entidadesCollection);
      });
    });

    describe('compareEntidades', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEntidades(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEntidades(entity1, entity2);
        const compareResult2 = service.compareEntidades(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEntidades(entity1, entity2);
        const compareResult2 = service.compareEntidades(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEntidades(entity1, entity2);
        const compareResult2 = service.compareEntidades(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
