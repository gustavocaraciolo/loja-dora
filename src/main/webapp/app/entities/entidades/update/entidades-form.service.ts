import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEntidades, NewEntidades } from '../entidades.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEntidades for edit and NewEntidadesFormGroupInput for create.
 */
type EntidadesFormGroupInput = IEntidades | PartialWithRequiredKeyOf<NewEntidades>;

type EntidadesFormDefaults = Pick<NewEntidades, 'id'>;

type EntidadesFormGroupContent = {
  id: FormControl<IEntidades['id'] | NewEntidades['id']>;
  nome: FormControl<IEntidades['nome']>;
  endereco: FormControl<IEntidades['endereco']>;
  telefone: FormControl<IEntidades['telefone']>;
};

export type EntidadesFormGroup = FormGroup<EntidadesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EntidadesFormService {
  createEntidadesFormGroup(entidades: EntidadesFormGroupInput = { id: null }): EntidadesFormGroup {
    const entidadesRawValue = {
      ...this.getFormDefaults(),
      ...entidades,
    };
    return new FormGroup<EntidadesFormGroupContent>({
      id: new FormControl(
        { value: entidadesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(entidadesRawValue.nome),
      endereco: new FormControl(entidadesRawValue.endereco),
      telefone: new FormControl(entidadesRawValue.telefone),
    });
  }

  getEntidades(form: EntidadesFormGroup): IEntidades | NewEntidades {
    return form.getRawValue() as IEntidades | NewEntidades;
  }

  resetForm(form: EntidadesFormGroup, entidades: EntidadesFormGroupInput): void {
    const entidadesRawValue = { ...this.getFormDefaults(), ...entidades };
    form.reset(
      {
        ...entidadesRawValue,
        id: { value: entidadesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EntidadesFormDefaults {
    return {
      id: null,
    };
  }
}
