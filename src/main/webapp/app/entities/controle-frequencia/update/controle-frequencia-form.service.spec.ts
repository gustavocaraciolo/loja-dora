import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../controle-frequencia.test-samples';

import { ControleFrequenciaFormService } from './controle-frequencia-form.service';

describe('ControleFrequencia Form Service', () => {
  let service: ControleFrequenciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControleFrequenciaFormService);
  });

  describe('Service methods', () => {
    describe('createControleFrequenciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createControleFrequenciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataTrabalho: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });

      it('passing IControleFrequencia should create a new form with FormGroup', () => {
        const formGroup = service.createControleFrequenciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataTrabalho: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getControleFrequencia', () => {
      it('should return NewControleFrequencia for default ControleFrequencia initial value', () => {
        const formGroup = service.createControleFrequenciaFormGroup(sampleWithNewData);

        const controleFrequencia = service.getControleFrequencia(formGroup) as any;

        expect(controleFrequencia).toMatchObject(sampleWithNewData);
      });

      it('should return NewControleFrequencia for empty ControleFrequencia initial value', () => {
        const formGroup = service.createControleFrequenciaFormGroup();

        const controleFrequencia = service.getControleFrequencia(formGroup) as any;

        expect(controleFrequencia).toMatchObject({});
      });

      it('should return IControleFrequencia', () => {
        const formGroup = service.createControleFrequenciaFormGroup(sampleWithRequiredData);

        const controleFrequencia = service.getControleFrequencia(formGroup) as any;

        expect(controleFrequencia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IControleFrequencia should not enable id FormControl', () => {
        const formGroup = service.createControleFrequenciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewControleFrequencia should disable id FormControl', () => {
        const formGroup = service.createControleFrequenciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
