import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IControlePagamentoFuncionario, NewControlePagamentoFuncionario } from '../controle-pagamento-funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IControlePagamentoFuncionario for edit and NewControlePagamentoFuncionarioFormGroupInput for create.
 */
type ControlePagamentoFuncionarioFormGroupInput = IControlePagamentoFuncionario | PartialWithRequiredKeyOf<NewControlePagamentoFuncionario>;

type ControlePagamentoFuncionarioFormDefaults = Pick<NewControlePagamentoFuncionario, 'id'>;

type ControlePagamentoFuncionarioFormGroupContent = {
  id: FormControl<IControlePagamentoFuncionario['id'] | NewControlePagamentoFuncionario['id']>;
  data: FormControl<IControlePagamentoFuncionario['data']>;
  salario: FormControl<IControlePagamentoFuncionario['salario']>;
  beneficio: FormControl<IControlePagamentoFuncionario['beneficio']>;
  comissao: FormControl<IControlePagamentoFuncionario['comissao']>;
  ferias: FormControl<IControlePagamentoFuncionario['ferias']>;
  adiantamento: FormControl<IControlePagamentoFuncionario['adiantamento']>;
  total: FormControl<IControlePagamentoFuncionario['total']>;
  funcionario: FormControl<IControlePagamentoFuncionario['funcionario']>;
};

export type ControlePagamentoFuncionarioFormGroup = FormGroup<ControlePagamentoFuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ControlePagamentoFuncionarioFormService {
  createControlePagamentoFuncionarioFormGroup(
    controlePagamentoFuncionario: ControlePagamentoFuncionarioFormGroupInput = { id: null },
  ): ControlePagamentoFuncionarioFormGroup {
    const controlePagamentoFuncionarioRawValue = {
      ...this.getFormDefaults(),
      ...controlePagamentoFuncionario,
    };
    return new FormGroup<ControlePagamentoFuncionarioFormGroupContent>({
      id: new FormControl(
        { value: controlePagamentoFuncionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      data: new FormControl(controlePagamentoFuncionarioRawValue.data),
      salario: new FormControl(controlePagamentoFuncionarioRawValue.salario),
      beneficio: new FormControl(controlePagamentoFuncionarioRawValue.beneficio),
      comissao: new FormControl(controlePagamentoFuncionarioRawValue.comissao),
      ferias: new FormControl(controlePagamentoFuncionarioRawValue.ferias),
      adiantamento: new FormControl(controlePagamentoFuncionarioRawValue.adiantamento),
      total: new FormControl(controlePagamentoFuncionarioRawValue.total),
      funcionario: new FormControl(controlePagamentoFuncionarioRawValue.funcionario),
    });
  }

  getControlePagamentoFuncionario(
    form: ControlePagamentoFuncionarioFormGroup,
  ): IControlePagamentoFuncionario | NewControlePagamentoFuncionario {
    return form.getRawValue() as IControlePagamentoFuncionario | NewControlePagamentoFuncionario;
  }

  resetForm(form: ControlePagamentoFuncionarioFormGroup, controlePagamentoFuncionario: ControlePagamentoFuncionarioFormGroupInput): void {
    const controlePagamentoFuncionarioRawValue = { ...this.getFormDefaults(), ...controlePagamentoFuncionario };
    form.reset(
      {
        ...controlePagamentoFuncionarioRawValue,
        id: { value: controlePagamentoFuncionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ControlePagamentoFuncionarioFormDefaults {
    return {
      id: null,
    };
  }
}
