import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IControleEntregas } from '../controle-entregas.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../controle-entregas.test-samples';

import { ControleEntregasService, RestControleEntregas } from './controle-entregas.service';

const requireRestSample: RestControleEntregas = {
  ...sampleWithRequiredData,
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
};

describe('ControleEntregas Service', () => {
  let service: ControleEntregasService;
  let httpMock: HttpTestingController;
  let expectedResult: IControleEntregas | IControleEntregas[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ControleEntregasService);
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

    it('should create a ControleEntregas', () => {
      const controleEntregas = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(controleEntregas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ControleEntregas', () => {
      const controleEntregas = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(controleEntregas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ControleEntregas', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ControleEntregas', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ControleEntregas', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addControleEntregasToCollectionIfMissing', () => {
      it('should add a ControleEntregas to an empty array', () => {
        const controleEntregas: IControleEntregas = sampleWithRequiredData;
        expectedResult = service.addControleEntregasToCollectionIfMissing([], controleEntregas);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleEntregas);
      });

      it('should not add a ControleEntregas to an array that contains it', () => {
        const controleEntregas: IControleEntregas = sampleWithRequiredData;
        const controleEntregasCollection: IControleEntregas[] = [
          {
            ...controleEntregas,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addControleEntregasToCollectionIfMissing(controleEntregasCollection, controleEntregas);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ControleEntregas to an array that doesn't contain it", () => {
        const controleEntregas: IControleEntregas = sampleWithRequiredData;
        const controleEntregasCollection: IControleEntregas[] = [sampleWithPartialData];
        expectedResult = service.addControleEntregasToCollectionIfMissing(controleEntregasCollection, controleEntregas);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleEntregas);
      });

      it('should add only unique ControleEntregas to an array', () => {
        const controleEntregasArray: IControleEntregas[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const controleEntregasCollection: IControleEntregas[] = [sampleWithRequiredData];
        expectedResult = service.addControleEntregasToCollectionIfMissing(controleEntregasCollection, ...controleEntregasArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const controleEntregas: IControleEntregas = sampleWithRequiredData;
        const controleEntregas2: IControleEntregas = sampleWithPartialData;
        expectedResult = service.addControleEntregasToCollectionIfMissing([], controleEntregas, controleEntregas2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleEntregas);
        expect(expectedResult).toContain(controleEntregas2);
      });

      it('should accept null and undefined values', () => {
        const controleEntregas: IControleEntregas = sampleWithRequiredData;
        expectedResult = service.addControleEntregasToCollectionIfMissing([], null, controleEntregas, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleEntregas);
      });

      it('should return initial array if no ControleEntregas is added', () => {
        const controleEntregasCollection: IControleEntregas[] = [sampleWithRequiredData];
        expectedResult = service.addControleEntregasToCollectionIfMissing(controleEntregasCollection, undefined, null);
        expect(expectedResult).toEqual(controleEntregasCollection);
      });
    });

    describe('compareControleEntregas', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareControleEntregas(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareControleEntregas(entity1, entity2);
        const compareResult2 = service.compareControleEntregas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareControleEntregas(entity1, entity2);
        const compareResult2 = service.compareControleEntregas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareControleEntregas(entity1, entity2);
        const compareResult2 = service.compareControleEntregas(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
