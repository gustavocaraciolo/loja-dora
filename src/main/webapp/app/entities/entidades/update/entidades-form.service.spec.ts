import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../entidades.test-samples';

import { EntidadesFormService } from './entidades-form.service';

describe('Entidades Form Service', () => {
  let service: EntidadesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EntidadesFormService);
  });

  describe('Service methods', () => {
    describe('createEntidadesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEntidadesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            endereco: expect.any(Object),
            telefone: expect.any(Object),
          }),
        );
      });

      it('passing IEntidades should create a new form with FormGroup', () => {
        const formGroup = service.createEntidadesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            endereco: expect.any(Object),
            telefone: expect.any(Object),
          }),
        );
      });
    });

    describe('getEntidades', () => {
      it('should return NewEntidades for default Entidades initial value', () => {
        const formGroup = service.createEntidadesFormGroup(sampleWithNewData);

        const entidades = service.getEntidades(formGroup) as any;

        expect(entidades).toMatchObject(sampleWithNewData);
      });

      it('should return NewEntidades for empty Entidades initial value', () => {
        const formGroup = service.createEntidadesFormGroup();

        const entidades = service.getEntidades(formGroup) as any;

        expect(entidades).toMatchObject({});
      });

      it('should return IEntidades', () => {
        const formGroup = service.createEntidadesFormGroup(sampleWithRequiredData);

        const entidades = service.getEntidades(formGroup) as any;

        expect(entidades).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEntidades should not enable id FormControl', () => {
        const formGroup = service.createEntidadesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEntidades should disable id FormControl', () => {
        const formGroup = service.createEntidadesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
