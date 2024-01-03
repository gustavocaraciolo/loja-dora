import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IControleAjustes } from '../controle-ajustes.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../controle-ajustes.test-samples';

import { ControleAjustesService, RestControleAjustes } from './controle-ajustes.service';

const requireRestSample: RestControleAjustes = {
  ...sampleWithRequiredData,
  dataEntrega: sampleWithRequiredData.dataEntrega?.format(DATE_FORMAT),
  dataRecebimento: sampleWithRequiredData.dataRecebimento?.format(DATE_FORMAT),
};

describe('ControleAjustes Service', () => {
  let service: ControleAjustesService;
  let httpMock: HttpTestingController;
  let expectedResult: IControleAjustes | IControleAjustes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ControleAjustesService);
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

    it('should create a ControleAjustes', () => {
      const controleAjustes = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(controleAjustes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ControleAjustes', () => {
      const controleAjustes = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(controleAjustes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ControleAjustes', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ControleAjustes', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ControleAjustes', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addControleAjustesToCollectionIfMissing', () => {
      it('should add a ControleAjustes to an empty array', () => {
        const controleAjustes: IControleAjustes = sampleWithRequiredData;
        expectedResult = service.addControleAjustesToCollectionIfMissing([], controleAjustes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleAjustes);
      });

      it('should not add a ControleAjustes to an array that contains it', () => {
        const controleAjustes: IControleAjustes = sampleWithRequiredData;
        const controleAjustesCollection: IControleAjustes[] = [
          {
            ...controleAjustes,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addControleAjustesToCollectionIfMissing(controleAjustesCollection, controleAjustes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ControleAjustes to an array that doesn't contain it", () => {
        const controleAjustes: IControleAjustes = sampleWithRequiredData;
        const controleAjustesCollection: IControleAjustes[] = [sampleWithPartialData];
        expectedResult = service.addControleAjustesToCollectionIfMissing(controleAjustesCollection, controleAjustes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleAjustes);
      });

      it('should add only unique ControleAjustes to an array', () => {
        const controleAjustesArray: IControleAjustes[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const controleAjustesCollection: IControleAjustes[] = [sampleWithRequiredData];
        expectedResult = service.addControleAjustesToCollectionIfMissing(controleAjustesCollection, ...controleAjustesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const controleAjustes: IControleAjustes = sampleWithRequiredData;
        const controleAjustes2: IControleAjustes = sampleWithPartialData;
        expectedResult = service.addControleAjustesToCollectionIfMissing([], controleAjustes, controleAjustes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleAjustes);
        expect(expectedResult).toContain(controleAjustes2);
      });

      it('should accept null and undefined values', () => {
        const controleAjustes: IControleAjustes = sampleWithRequiredData;
        expectedResult = service.addControleAjustesToCollectionIfMissing([], null, controleAjustes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleAjustes);
      });

      it('should return initial array if no ControleAjustes is added', () => {
        const controleAjustesCollection: IControleAjustes[] = [sampleWithRequiredData];
        expectedResult = service.addControleAjustesToCollectionIfMissing(controleAjustesCollection, undefined, null);
        expect(expectedResult).toEqual(controleAjustesCollection);
      });
    });

    describe('compareControleAjustes', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareControleAjustes(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareControleAjustes(entity1, entity2);
        const compareResult2 = service.compareControleAjustes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareControleAjustes(entity1, entity2);
        const compareResult2 = service.compareControleAjustes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareControleAjustes(entity1, entity2);
        const compareResult2 = service.compareControleAjustes(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
