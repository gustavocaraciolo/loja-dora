import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFuncionario, NewFuncionario } from '../funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuncionario for edit and NewFuncionarioFormGroupInput for create.
 */
type FuncionarioFormGroupInput = IFuncionario | PartialWithRequiredKeyOf<NewFuncionario>;

type FuncionarioFormDefaults = Pick<NewFuncionario, 'id'>;

type FuncionarioFormGroupContent = {
  id: FormControl<IFuncionario['id'] | NewFuncionario['id']>;
  primeiroNome: FormControl<IFuncionario['primeiroNome']>;
  ultimoNome: FormControl<IFuncionario['ultimoNome']>;
  enderecoLinha1: FormControl<IFuncionario['enderecoLinha1']>;
  enderecoLinha2: FormControl<IFuncionario['enderecoLinha2']>;
  dataInicio: FormControl<IFuncionario['dataInicio']>;
  telefone: FormControl<IFuncionario['telefone']>;
  telefoneEmergencial: FormControl<IFuncionario['telefoneEmergencial']>;
  email: FormControl<IFuncionario['email']>;
  banco: FormControl<IFuncionario['banco']>;
  iban: FormControl<IFuncionario['iban']>;
};

export type FuncionarioFormGroup = FormGroup<FuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuncionarioFormService {
  createFuncionarioFormGroup(funcionario: FuncionarioFormGroupInput = { id: null }): FuncionarioFormGroup {
    const funcionarioRawValue = {
      ...this.getFormDefaults(),
      ...funcionario,
    };
    return new FormGroup<FuncionarioFormGroupContent>({
      id: new FormControl(
        { value: funcionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      primeiroNome: new FormControl(funcionarioRawValue.primeiroNome, {
        validators: [Validators.required],
      }),
      ultimoNome: new FormControl(funcionarioRawValue.ultimoNome, {
        validators: [Validators.required],
      }),
      enderecoLinha1: new FormControl(funcionarioRawValue.enderecoLinha1),
      enderecoLinha2: new FormControl(funcionarioRawValue.enderecoLinha2),
      dataInicio: new FormControl(funcionarioRawValue.dataInicio),
      telefone: new FormControl(funcionarioRawValue.telefone),
      telefoneEmergencial: new FormControl(funcionarioRawValue.telefoneEmergencial),
      email: new FormControl(funcionarioRawValue.email),
      banco: new FormControl(funcionarioRawValue.banco),
      iban: new FormControl(funcionarioRawValue.iban),
    });
  }

  getFuncionario(form: FuncionarioFormGroup): IFuncionario | NewFuncionario {
    return form.getRawValue() as IFuncionario | NewFuncionario;
  }

  resetForm(form: FuncionarioFormGroup, funcionario: FuncionarioFormGroupInput): void {
    const funcionarioRawValue = { ...this.getFormDefaults(), ...funcionario };
    form.reset(
      {
        ...funcionarioRawValue,
        id: { value: funcionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuncionarioFormDefaults {
    return {
      id: null,
    };
  }
}
