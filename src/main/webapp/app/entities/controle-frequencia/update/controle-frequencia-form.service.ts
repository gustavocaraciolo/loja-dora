import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IControleFrequencia, NewControleFrequencia } from '../controle-frequencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IControleFrequencia for edit and NewControleFrequenciaFormGroupInput for create.
 */
type ControleFrequenciaFormGroupInput = IControleFrequencia | PartialWithRequiredKeyOf<NewControleFrequencia>;

type ControleFrequenciaFormDefaults = Pick<NewControleFrequencia, 'id' | 'funcionarios'>;

type ControleFrequenciaFormGroupContent = {
  id: FormControl<IControleFrequencia['id'] | NewControleFrequencia['id']>;
  dataTrabalho: FormControl<IControleFrequencia['dataTrabalho']>;
  funcionarios: FormControl<IControleFrequencia['funcionarios']>;
};

export type ControleFrequenciaFormGroup = FormGroup<ControleFrequenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ControleFrequenciaFormService {
  createControleFrequenciaFormGroup(controleFrequencia: ControleFrequenciaFormGroupInput = { id: null }): ControleFrequenciaFormGroup {
    const controleFrequenciaRawValue = {
      ...this.getFormDefaults(),
      ...controleFrequencia,
    };
    return new FormGroup<ControleFrequenciaFormGroupContent>({
      id: new FormControl(
        { value: controleFrequenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataTrabalho: new FormControl(controleFrequenciaRawValue.dataTrabalho),
      funcionarios: new FormControl(controleFrequenciaRawValue.funcionarios ?? []),
    });
  }

  getControleFrequencia(form: ControleFrequenciaFormGroup): IControleFrequencia | NewControleFrequencia {
    return form.getRawValue() as IControleFrequencia | NewControleFrequencia;
  }

  resetForm(form: ControleFrequenciaFormGroup, controleFrequencia: ControleFrequenciaFormGroupInput): void {
    const controleFrequenciaRawValue = { ...this.getFormDefaults(), ...controleFrequencia };
    form.reset(
      {
        ...controleFrequenciaRawValue,
        id: { value: controleFrequenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ControleFrequenciaFormDefaults {
    return {
      id: null,
      funcionarios: [],
    };
  }
}
