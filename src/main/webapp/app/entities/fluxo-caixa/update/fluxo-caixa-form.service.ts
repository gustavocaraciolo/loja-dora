import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFluxoCaixa, NewFluxoCaixa } from '../fluxo-caixa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFluxoCaixa for edit and NewFluxoCaixaFormGroupInput for create.
 */
type FluxoCaixaFormGroupInput = IFluxoCaixa | PartialWithRequiredKeyOf<NewFluxoCaixa>;

type FluxoCaixaFormDefaults = Pick<NewFluxoCaixa, 'id' | 'entidades'>;

type FluxoCaixaFormGroupContent = {
  id: FormControl<IFluxoCaixa['id'] | NewFluxoCaixa['id']>;
  data: FormControl<IFluxoCaixa['data']>;
  saldo: FormControl<IFluxoCaixa['saldo']>;
  banco: FormControl<IFluxoCaixa['banco']>;
  valor: FormControl<IFluxoCaixa['valor']>;
  fixoVariavel: FormControl<IFluxoCaixa['fixoVariavel']>;
  entradaSaida: FormControl<IFluxoCaixa['entradaSaida']>;
  pais: FormControl<IFluxoCaixa['pais']>;
  entidades: FormControl<IFluxoCaixa['entidades']>;
};

export type FluxoCaixaFormGroup = FormGroup<FluxoCaixaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FluxoCaixaFormService {
  createFluxoCaixaFormGroup(fluxoCaixa: FluxoCaixaFormGroupInput = { id: null }): FluxoCaixaFormGroup {
    const fluxoCaixaRawValue = {
      ...this.getFormDefaults(),
      ...fluxoCaixa,
    };
    return new FormGroup<FluxoCaixaFormGroupContent>({
      id: new FormControl(
        { value: fluxoCaixaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      data: new FormControl(fluxoCaixaRawValue.data),
      saldo: new FormControl(fluxoCaixaRawValue.saldo),
      banco: new FormControl(fluxoCaixaRawValue.banco),
      valor: new FormControl(fluxoCaixaRawValue.valor),
      fixoVariavel: new FormControl(fluxoCaixaRawValue.fixoVariavel),
      entradaSaida: new FormControl(fluxoCaixaRawValue.entradaSaida),
      pais: new FormControl(fluxoCaixaRawValue.pais),
      entidades: new FormControl(fluxoCaixaRawValue.entidades ?? []),
    });
  }

  getFluxoCaixa(form: FluxoCaixaFormGroup): IFluxoCaixa | NewFluxoCaixa {
    return form.getRawValue() as IFluxoCaixa | NewFluxoCaixa;
  }

  resetForm(form: FluxoCaixaFormGroup, fluxoCaixa: FluxoCaixaFormGroupInput): void {
    const fluxoCaixaRawValue = { ...this.getFormDefaults(), ...fluxoCaixa };
    form.reset(
      {
        ...fluxoCaixaRawValue,
        id: { value: fluxoCaixaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FluxoCaixaFormDefaults {
    return {
      id: null,
      entidades: [],
    };
  }
}
