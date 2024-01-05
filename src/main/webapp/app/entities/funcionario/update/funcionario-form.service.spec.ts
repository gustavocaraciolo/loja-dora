import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../funcionario.test-samples';

import { FuncionarioFormService } from './funcionario-form.service';

describe('Funcionario Form Service', () => {
  let service: FuncionarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuncionarioFormService);
  });

  describe('Service methods', () => {
    describe('createFuncionarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuncionarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            primeiroNome: expect.any(Object),
            ultimoNome: expect.any(Object),
            enderecoLinha1: expect.any(Object),
            enderecoLinha2: expect.any(Object),
            dataInicio: expect.any(Object),
            telefone: expect.any(Object),
            telefoneEmergencial: expect.any(Object),
            email: expect.any(Object),
            banco: expect.any(Object),
            iban: expect.any(Object),
          }),
        );
      });

      it('passing IFuncionario should create a new form with FormGroup', () => {
        const formGroup = service.createFuncionarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            primeiroNome: expect.any(Object),
            ultimoNome: expect.any(Object),
            enderecoLinha1: expect.any(Object),
            enderecoLinha2: expect.any(Object),
            dataInicio: expect.any(Object),
            telefone: expect.any(Object),
            telefoneEmergencial: expect.any(Object),
            email: expect.any(Object),
            banco: expect.any(Object),
            iban: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuncionario', () => {
      it('should return NewFuncionario for default Funcionario initial value', () => {
        const formGroup = service.createFuncionarioFormGroup(sampleWithNewData);

        const funcionario = service.getFuncionario(formGroup) as any;

        expect(funcionario).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuncionario for empty Funcionario initial value', () => {
        const formGroup = service.createFuncionarioFormGroup();

        const funcionario = service.getFuncionario(formGroup) as any;

        expect(funcionario).toMatchObject({});
      });

      it('should return IFuncionario', () => {
        const formGroup = service.createFuncionarioFormGroup(sampleWithRequiredData);

        const funcionario = service.getFuncionario(formGroup) as any;

        expect(funcionario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuncionario should not enable id FormControl', () => {
        const formGroup = service.createFuncionarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuncionario should disable id FormControl', () => {
        const formGroup = service.createFuncionarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
