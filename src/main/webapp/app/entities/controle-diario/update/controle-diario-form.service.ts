import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IControleDiario, NewControleDiario } from '../controle-diario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IControleDiario for edit and NewControleDiarioFormGroupInput for create.
 */
type ControleDiarioFormGroupInput = IControleDiario | PartialWithRequiredKeyOf<NewControleDiario>;

type ControleDiarioFormDefaults = Pick<NewControleDiario, 'id'>;

type ControleDiarioFormGroupContent = {
  id: FormControl<IControleDiario['id'] | NewControleDiario['id']>;
  data: FormControl<IControleDiario['data']>;
  cliente: FormControl<IControleDiario['cliente']>;
  valorCompra: FormControl<IControleDiario['valorCompra']>;
  valorPago: FormControl<IControleDiario['valorPago']>;
  saldoDevedor: FormControl<IControleDiario['saldoDevedor']>;
  recebimento: FormControl<IControleDiario['recebimento']>;
  pagamento: FormControl<IControleDiario['pagamento']>;
  banco: FormControl<IControleDiario['banco']>;
};

export type ControleDiarioFormGroup = FormGroup<ControleDiarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ControleDiarioFormService {
  createControleDiarioFormGroup(controleDiario: ControleDiarioFormGroupInput = { id: null }): ControleDiarioFormGroup {
    const controleDiarioRawValue = {
      ...this.getFormDefaults(),
      ...controleDiario,
    };
    return new FormGroup<ControleDiarioFormGroupContent>({
      id: new FormControl(
        { value: controleDiarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      data: new FormControl(controleDiarioRawValue.data),
      cliente: new FormControl(controleDiarioRawValue.cliente),
      valorCompra: new FormControl(controleDiarioRawValue.valorCompra),
      valorPago: new FormControl(controleDiarioRawValue.valorPago),
      saldoDevedor: new FormControl(controleDiarioRawValue.saldoDevedor),
      recebimento: new FormControl(controleDiarioRawValue.recebimento),
      pagamento: new FormControl(controleDiarioRawValue.pagamento),
      banco: new FormControl(controleDiarioRawValue.banco),
    });
  }

  getControleDiario(form: ControleDiarioFormGroup): IControleDiario | NewControleDiario {
    return form.getRawValue() as IControleDiario | NewControleDiario;
  }

  resetForm(form: ControleDiarioFormGroup, controleDiario: ControleDiarioFormGroupInput): void {
    const controleDiarioRawValue = { ...this.getFormDefaults(), ...controleDiario };
    form.reset(
      {
        ...controleDiarioRawValue,
        id: { value: controleDiarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ControleDiarioFormDefaults {
    return {
      id: null,
    };
  }
}
