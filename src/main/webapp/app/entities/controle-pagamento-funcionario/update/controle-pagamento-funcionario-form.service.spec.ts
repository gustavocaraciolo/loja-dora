import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../controle-pagamento-funcionario.test-samples';

import { ControlePagamentoFuncionarioFormService } from './controle-pagamento-funcionario-form.service';

describe('ControlePagamentoFuncionario Form Service', () => {
  let service: ControlePagamentoFuncionarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControlePagamentoFuncionarioFormService);
  });

  describe('Service methods', () => {
    describe('createControlePagamentoFuncionarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createControlePagamentoFuncionarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            salario: expect.any(Object),
            beneficio: expect.any(Object),
            comissao: expect.any(Object),
            ferias: expect.any(Object),
            adiantamento: expect.any(Object),
            total: expect.any(Object),
            funcionarios: expect.any(Object),
          }),
        );
      });

      it('passing IControlePagamentoFuncionario should create a new form with FormGroup', () => {
        const formGroup = service.createControlePagamentoFuncionarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            salario: expect.any(Object),
            beneficio: expect.any(Object),
            comissao: expect.any(Object),
            ferias: expect.any(Object),
            adiantamento: expect.any(Object),
            total: expect.any(Object),
            funcionarios: expect.any(Object),
          }),
        );
      });
    });

    describe('getControlePagamentoFuncionario', () => {
      it('should return NewControlePagamentoFuncionario for default ControlePagamentoFuncionario initial value', () => {
        const formGroup = service.createControlePagamentoFuncionarioFormGroup(sampleWithNewData);

        const controlePagamentoFuncionario = service.getControlePagamentoFuncionario(formGroup) as any;

        expect(controlePagamentoFuncionario).toMatchObject(sampleWithNewData);
      });

      it('should return NewControlePagamentoFuncionario for empty ControlePagamentoFuncionario initial value', () => {
        const formGroup = service.createControlePagamentoFuncionarioFormGroup();

        const controlePagamentoFuncionario = service.getControlePagamentoFuncionario(formGroup) as any;

        expect(controlePagamentoFuncionario).toMatchObject({});
      });

      it('should return IControlePagamentoFuncionario', () => {
        const formGroup = service.createControlePagamentoFuncionarioFormGroup(sampleWithRequiredData);

        const controlePagamentoFuncionario = service.getControlePagamentoFuncionario(formGroup) as any;

        expect(controlePagamentoFuncionario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IControlePagamentoFuncionario should not enable id FormControl', () => {
        const formGroup = service.createControlePagamentoFuncionarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewControlePagamentoFuncionario should disable id FormControl', () => {
        const formGroup = service.createControlePagamentoFuncionarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
