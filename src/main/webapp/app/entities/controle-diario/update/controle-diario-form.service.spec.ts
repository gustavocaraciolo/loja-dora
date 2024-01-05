import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../controle-diario.test-samples';

import { ControleDiarioFormService } from './controle-diario-form.service';

describe('ControleDiario Form Service', () => {
  let service: ControleDiarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControleDiarioFormService);
  });

  describe('Service methods', () => {
    describe('createControleDiarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createControleDiarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            cliente: expect.any(Object),
            valorCompra: expect.any(Object),
            valorPago: expect.any(Object),
            saldoDevedor: expect.any(Object),
            recebimento: expect.any(Object),
            pagamento: expect.any(Object),
            banco: expect.any(Object),
          }),
        );
      });

      it('passing IControleDiario should create a new form with FormGroup', () => {
        const formGroup = service.createControleDiarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            cliente: expect.any(Object),
            valorCompra: expect.any(Object),
            valorPago: expect.any(Object),
            saldoDevedor: expect.any(Object),
            recebimento: expect.any(Object),
            pagamento: expect.any(Object),
            banco: expect.any(Object),
          }),
        );
      });
    });

    describe('getControleDiario', () => {
      it('should return NewControleDiario for default ControleDiario initial value', () => {
        const formGroup = service.createControleDiarioFormGroup(sampleWithNewData);

        const controleDiario = service.getControleDiario(formGroup) as any;

        expect(controleDiario).toMatchObject(sampleWithNewData);
      });

      it('should return NewControleDiario for empty ControleDiario initial value', () => {
        const formGroup = service.createControleDiarioFormGroup();

        const controleDiario = service.getControleDiario(formGroup) as any;

        expect(controleDiario).toMatchObject({});
      });

      it('should return IControleDiario', () => {
        const formGroup = service.createControleDiarioFormGroup(sampleWithRequiredData);

        const controleDiario = service.getControleDiario(formGroup) as any;

        expect(controleDiario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IControleDiario should not enable id FormControl', () => {
        const formGroup = service.createControleDiarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewControleDiario should disable id FormControl', () => {
        const formGroup = service.createControleDiarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
