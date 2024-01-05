import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IControleFrequencia } from '../controle-frequencia.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../controle-frequencia.test-samples';

import { ControleFrequenciaService, RestControleFrequencia } from './controle-frequencia.service';

const requireRestSample: RestControleFrequencia = {
  ...sampleWithRequiredData,
  dataTrabalho: sampleWithRequiredData.dataTrabalho?.format(DATE_FORMAT),
};

describe('ControleFrequencia Service', () => {
  let service: ControleFrequenciaService;
  let httpMock: HttpTestingController;
  let expectedResult: IControleFrequencia | IControleFrequencia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ControleFrequenciaService);
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

    it('should create a ControleFrequencia', () => {
      const controleFrequencia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(controleFrequencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ControleFrequencia', () => {
      const controleFrequencia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(controleFrequencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ControleFrequencia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ControleFrequencia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ControleFrequencia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addControleFrequenciaToCollectionIfMissing', () => {
      it('should add a ControleFrequencia to an empty array', () => {
        const controleFrequencia: IControleFrequencia = sampleWithRequiredData;
        expectedResult = service.addControleFrequenciaToCollectionIfMissing([], controleFrequencia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleFrequencia);
      });

      it('should not add a ControleFrequencia to an array that contains it', () => {
        const controleFrequencia: IControleFrequencia = sampleWithRequiredData;
        const controleFrequenciaCollection: IControleFrequencia[] = [
          {
            ...controleFrequencia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addControleFrequenciaToCollectionIfMissing(controleFrequenciaCollection, controleFrequencia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ControleFrequencia to an array that doesn't contain it", () => {
        const controleFrequencia: IControleFrequencia = sampleWithRequiredData;
        const controleFrequenciaCollection: IControleFrequencia[] = [sampleWithPartialData];
        expectedResult = service.addControleFrequenciaToCollectionIfMissing(controleFrequenciaCollection, controleFrequencia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleFrequencia);
      });

      it('should add only unique ControleFrequencia to an array', () => {
        const controleFrequenciaArray: IControleFrequencia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const controleFrequenciaCollection: IControleFrequencia[] = [sampleWithRequiredData];
        expectedResult = service.addControleFrequenciaToCollectionIfMissing(controleFrequenciaCollection, ...controleFrequenciaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const controleFrequencia: IControleFrequencia = sampleWithRequiredData;
        const controleFrequencia2: IControleFrequencia = sampleWithPartialData;
        expectedResult = service.addControleFrequenciaToCollectionIfMissing([], controleFrequencia, controleFrequencia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleFrequencia);
        expect(expectedResult).toContain(controleFrequencia2);
      });

      it('should accept null and undefined values', () => {
        const controleFrequencia: IControleFrequencia = sampleWithRequiredData;
        expectedResult = service.addControleFrequenciaToCollectionIfMissing([], null, controleFrequencia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleFrequencia);
      });

      it('should return initial array if no ControleFrequencia is added', () => {
        const controleFrequenciaCollection: IControleFrequencia[] = [sampleWithRequiredData];
        expectedResult = service.addControleFrequenciaToCollectionIfMissing(controleFrequenciaCollection, undefined, null);
        expect(expectedResult).toEqual(controleFrequenciaCollection);
      });
    });

    describe('compareControleFrequencia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareControleFrequencia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareControleFrequencia(entity1, entity2);
        const compareResult2 = service.compareControleFrequencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareControleFrequencia(entity1, entity2);
        const compareResult2 = service.compareControleFrequencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareControleFrequencia(entity1, entity2);
        const compareResult2 = service.compareControleFrequencia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
