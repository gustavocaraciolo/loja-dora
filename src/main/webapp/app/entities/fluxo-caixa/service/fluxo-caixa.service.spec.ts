import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFluxoCaixa } from '../fluxo-caixa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fluxo-caixa.test-samples';

import { FluxoCaixaService, RestFluxoCaixa } from './fluxo-caixa.service';

const requireRestSample: RestFluxoCaixa = {
  ...sampleWithRequiredData,
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
};

describe('FluxoCaixa Service', () => {
  let service: FluxoCaixaService;
  let httpMock: HttpTestingController;
  let expectedResult: IFluxoCaixa | IFluxoCaixa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FluxoCaixaService);
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

    it('should create a FluxoCaixa', () => {
      const fluxoCaixa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fluxoCaixa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FluxoCaixa', () => {
      const fluxoCaixa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fluxoCaixa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FluxoCaixa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FluxoCaixa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FluxoCaixa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFluxoCaixaToCollectionIfMissing', () => {
      it('should add a FluxoCaixa to an empty array', () => {
        const fluxoCaixa: IFluxoCaixa = sampleWithRequiredData;
        expectedResult = service.addFluxoCaixaToCollectionIfMissing([], fluxoCaixa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fluxoCaixa);
      });

      it('should not add a FluxoCaixa to an array that contains it', () => {
        const fluxoCaixa: IFluxoCaixa = sampleWithRequiredData;
        const fluxoCaixaCollection: IFluxoCaixa[] = [
          {
            ...fluxoCaixa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFluxoCaixaToCollectionIfMissing(fluxoCaixaCollection, fluxoCaixa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FluxoCaixa to an array that doesn't contain it", () => {
        const fluxoCaixa: IFluxoCaixa = sampleWithRequiredData;
        const fluxoCaixaCollection: IFluxoCaixa[] = [sampleWithPartialData];
        expectedResult = service.addFluxoCaixaToCollectionIfMissing(fluxoCaixaCollection, fluxoCaixa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fluxoCaixa);
      });

      it('should add only unique FluxoCaixa to an array', () => {
        const fluxoCaixaArray: IFluxoCaixa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fluxoCaixaCollection: IFluxoCaixa[] = [sampleWithRequiredData];
        expectedResult = service.addFluxoCaixaToCollectionIfMissing(fluxoCaixaCollection, ...fluxoCaixaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fluxoCaixa: IFluxoCaixa = sampleWithRequiredData;
        const fluxoCaixa2: IFluxoCaixa = sampleWithPartialData;
        expectedResult = service.addFluxoCaixaToCollectionIfMissing([], fluxoCaixa, fluxoCaixa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fluxoCaixa);
        expect(expectedResult).toContain(fluxoCaixa2);
      });

      it('should accept null and undefined values', () => {
        const fluxoCaixa: IFluxoCaixa = sampleWithRequiredData;
        expectedResult = service.addFluxoCaixaToCollectionIfMissing([], null, fluxoCaixa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fluxoCaixa);
      });

      it('should return initial array if no FluxoCaixa is added', () => {
        const fluxoCaixaCollection: IFluxoCaixa[] = [sampleWithRequiredData];
        expectedResult = service.addFluxoCaixaToCollectionIfMissing(fluxoCaixaCollection, undefined, null);
        expect(expectedResult).toEqual(fluxoCaixaCollection);
      });
    });

    describe('compareFluxoCaixa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFluxoCaixa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFluxoCaixa(entity1, entity2);
        const compareResult2 = service.compareFluxoCaixa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFluxoCaixa(entity1, entity2);
        const compareResult2 = service.compareFluxoCaixa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFluxoCaixa(entity1, entity2);
        const compareResult2 = service.compareFluxoCaixa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
