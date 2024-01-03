import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IControleEntregas, NewControleEntregas } from '../controle-entregas.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IControleEntregas for edit and NewControleEntregasFormGroupInput for create.
 */
type ControleEntregasFormGroupInput = IControleEntregas | PartialWithRequiredKeyOf<NewControleEntregas>;

type ControleEntregasFormDefaults = Pick<NewControleEntregas, 'id' | 'funcionarios'>;

type ControleEntregasFormGroupContent = {
  id: FormControl<IControleEntregas['id'] | NewControleEntregas['id']>;
  data: FormControl<IControleEntregas['data']>;
  descricao: FormControl<IControleEntregas['descricao']>;
  address: FormControl<IControleEntregas['address']>;
  receita: FormControl<IControleEntregas['receita']>;
  valor: FormControl<IControleEntregas['valor']>;
  funcionarios: FormControl<IControleEntregas['funcionarios']>;
};

export type ControleEntregasFormGroup = FormGroup<ControleEntregasFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ControleEntregasFormService {
  createControleEntregasFormGroup(controleEntregas: ControleEntregasFormGroupInput = { id: null }): ControleEntregasFormGroup {
    const controleEntregasRawValue = {
      ...this.getFormDefaults(),
      ...controleEntregas,
    };
    return new FormGroup<ControleEntregasFormGroupContent>({
      id: new FormControl(
        { value: controleEntregasRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      data: new FormControl(controleEntregasRawValue.data),
      descricao: new FormControl(controleEntregasRawValue.descricao),
      address: new FormControl(controleEntregasRawValue.address),
      receita: new FormControl(controleEntregasRawValue.receita),
      valor: new FormControl(controleEntregasRawValue.valor),
      funcionarios: new FormControl(controleEntregasRawValue.funcionarios ?? []),
    });
  }

  getControleEntregas(form: ControleEntregasFormGroup): IControleEntregas | NewControleEntregas {
    return form.getRawValue() as IControleEntregas | NewControleEntregas;
  }

  resetForm(form: ControleEntregasFormGroup, controleEntregas: ControleEntregasFormGroupInput): void {
    const controleEntregasRawValue = { ...this.getFormDefaults(), ...controleEntregas };
    form.reset(
      {
        ...controleEntregasRawValue,
        id: { value: controleEntregasRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ControleEntregasFormDefaults {
    return {
      id: null,
      funcionarios: [],
    };
  }
}
