import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../controle-entregas.test-samples';

import { ControleEntregasFormService } from './controle-entregas-form.service';

describe('ControleEntregas Form Service', () => {
  let service: ControleEntregasFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControleEntregasFormService);
  });

  describe('Service methods', () => {
    describe('createControleEntregasFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createControleEntregasFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            descricao: expect.any(Object),
            endereco: expect.any(Object),
            receita: expect.any(Object),
            valor: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });

      it('passing IControleEntregas should create a new form with FormGroup', () => {
        const formGroup = service.createControleEntregasFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            descricao: expect.any(Object),
            endereco: expect.any(Object),
            receita: expect.any(Object),
            valor: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getControleEntregas', () => {
      it('should return NewControleEntregas for default ControleEntregas initial value', () => {
        const formGroup = service.createControleEntregasFormGroup(sampleWithNewData);

        const controleEntregas = service.getControleEntregas(formGroup) as any;

        expect(controleEntregas).toMatchObject(sampleWithNewData);
      });

      it('should return NewControleEntregas for empty ControleEntregas initial value', () => {
        const formGroup = service.createControleEntregasFormGroup();

        const controleEntregas = service.getControleEntregas(formGroup) as any;

        expect(controleEntregas).toMatchObject({});
      });

      it('should return IControleEntregas', () => {
        const formGroup = service.createControleEntregasFormGroup(sampleWithRequiredData);

        const controleEntregas = service.getControleEntregas(formGroup) as any;

        expect(controleEntregas).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IControleEntregas should not enable id FormControl', () => {
        const formGroup = service.createControleEntregasFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewControleEntregas should disable id FormControl', () => {
        const formGroup = service.createControleEntregasFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
