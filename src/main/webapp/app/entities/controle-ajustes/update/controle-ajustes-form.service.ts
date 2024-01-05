import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IControleAjustes, NewControleAjustes } from '../controle-ajustes.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IControleAjustes for edit and NewControleAjustesFormGroupInput for create.
 */
type ControleAjustesFormGroupInput = IControleAjustes | PartialWithRequiredKeyOf<NewControleAjustes>;

type ControleAjustesFormDefaults = Pick<NewControleAjustes, 'id' | 'funcionarios'>;

type ControleAjustesFormGroupContent = {
  id: FormControl<IControleAjustes['id'] | NewControleAjustes['id']>;
  dataEntrega: FormControl<IControleAjustes['dataEntrega']>;
  dataRecebimento: FormControl<IControleAjustes['dataRecebimento']>;
  qtdPecas: FormControl<IControleAjustes['qtdPecas']>;
  descricao: FormControl<IControleAjustes['descricao']>;
  valor: FormControl<IControleAjustes['valor']>;
  receita: FormControl<IControleAjustes['receita']>;
  funcionarios: FormControl<IControleAjustes['funcionarios']>;
};

export type ControleAjustesFormGroup = FormGroup<ControleAjustesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ControleAjustesFormService {
  createControleAjustesFormGroup(controleAjustes: ControleAjustesFormGroupInput = { id: null }): ControleAjustesFormGroup {
    const controleAjustesRawValue = {
      ...this.getFormDefaults(),
      ...controleAjustes,
    };
    return new FormGroup<ControleAjustesFormGroupContent>({
      id: new FormControl(
        { value: controleAjustesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataEntrega: new FormControl(controleAjustesRawValue.dataEntrega),
      dataRecebimento: new FormControl(controleAjustesRawValue.dataRecebimento),
      qtdPecas: new FormControl(controleAjustesRawValue.qtdPecas),
      descricao: new FormControl(controleAjustesRawValue.descricao),
      valor: new FormControl(controleAjustesRawValue.valor),
      receita: new FormControl(controleAjustesRawValue.receita),
      funcionarios: new FormControl(controleAjustesRawValue.funcionarios ?? []),
    });
  }

  getControleAjustes(form: ControleAjustesFormGroup): IControleAjustes | NewControleAjustes {
    return form.getRawValue() as IControleAjustes | NewControleAjustes;
  }

  resetForm(form: ControleAjustesFormGroup, controleAjustes: ControleAjustesFormGroupInput): void {
    const controleAjustesRawValue = { ...this.getFormDefaults(), ...controleAjustes };
    form.reset(
      {
        ...controleAjustesRawValue,
        id: { value: controleAjustesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ControleAjustesFormDefaults {
    return {
      id: null,
      funcionarios: [],
    };
  }
}
