import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../controle-ajustes.test-samples';

import { ControleAjustesFormService } from './controle-ajustes-form.service';

describe('ControleAjustes Form Service', () => {
  let service: ControleAjustesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControleAjustesFormService);
  });

  describe('Service methods', () => {
    describe('createControleAjustesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createControleAjustesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataEntrega: expect.any(Object),
            dataRecebimento: expect.any(Object),
            qtdPecas: expect.any(Object),
            descricao: expect.any(Object),
            valor: expect.any(Object),
            receita: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });

      it('passing IControleAjustes should create a new form with FormGroup', () => {
        const formGroup = service.createControleAjustesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataEntrega: expect.any(Object),
            dataRecebimento: expect.any(Object),
            qtdPecas: expect.any(Object),
            descricao: expect.any(Object),
            valor: expect.any(Object),
            receita: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getControleAjustes', () => {
      it('should return NewControleAjustes for default ControleAjustes initial value', () => {
        const formGroup = service.createControleAjustesFormGroup(sampleWithNewData);

        const controleAjustes = service.getControleAjustes(formGroup) as any;

        expect(controleAjustes).toMatchObject(sampleWithNewData);
      });

      it('should return NewControleAjustes for empty ControleAjustes initial value', () => {
        const formGroup = service.createControleAjustesFormGroup();

        const controleAjustes = service.getControleAjustes(formGroup) as any;

        expect(controleAjustes).toMatchObject({});
      });

      it('should return IControleAjustes', () => {
        const formGroup = service.createControleAjustesFormGroup(sampleWithRequiredData);

        const controleAjustes = service.getControleAjustes(formGroup) as any;

        expect(controleAjustes).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IControleAjustes should not enable id FormControl', () => {
        const formGroup = service.createControleAjustesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewControleAjustes should disable id FormControl', () => {
        const formGroup = service.createControleAjustesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
