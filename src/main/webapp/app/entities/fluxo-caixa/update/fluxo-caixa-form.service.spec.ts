import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fluxo-caixa.test-samples';

import { FluxoCaixaFormService } from './fluxo-caixa-form.service';

describe('FluxoCaixa Form Service', () => {
  let service: FluxoCaixaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FluxoCaixaFormService);
  });

  describe('Service methods', () => {
    describe('createFluxoCaixaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFluxoCaixaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            saldo: expect.any(Object),
            banco: expect.any(Object),
            valor: expect.any(Object),
            fixoVariavel: expect.any(Object),
            entradaSaida: expect.any(Object),
            pais: expect.any(Object),
            entidades: expect.any(Object),
          }),
        );
      });

      it('passing IFluxoCaixa should create a new form with FormGroup', () => {
        const formGroup = service.createFluxoCaixaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            saldo: expect.any(Object),
            banco: expect.any(Object),
            valor: expect.any(Object),
            fixoVariavel: expect.any(Object),
            entradaSaida: expect.any(Object),
            pais: expect.any(Object),
            entidades: expect.any(Object),
          }),
        );
      });
    });

    describe('getFluxoCaixa', () => {
      it('should return NewFluxoCaixa for default FluxoCaixa initial value', () => {
        const formGroup = service.createFluxoCaixaFormGroup(sampleWithNewData);

        const fluxoCaixa = service.getFluxoCaixa(formGroup) as any;

        expect(fluxoCaixa).toMatchObject(sampleWithNewData);
      });

      it('should return NewFluxoCaixa for empty FluxoCaixa initial value', () => {
        const formGroup = service.createFluxoCaixaFormGroup();

        const fluxoCaixa = service.getFluxoCaixa(formGroup) as any;

        expect(fluxoCaixa).toMatchObject({});
      });

      it('should return IFluxoCaixa', () => {
        const formGroup = service.createFluxoCaixaFormGroup(sampleWithRequiredData);

        const fluxoCaixa = service.getFluxoCaixa(formGroup) as any;

        expect(fluxoCaixa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFluxoCaixa should not enable id FormControl', () => {
        const formGroup = service.createFluxoCaixaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFluxoCaixa should disable id FormControl', () => {
        const formGroup = service.createFluxoCaixaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
