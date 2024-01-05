import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IControlePagamentoFuncionario } from '../controle-pagamento-funcionario.model';
import { ControlePagamentoFuncionarioService } from '../service/controle-pagamento-funcionario.service';
import {
  ControlePagamentoFuncionarioFormService,
  ControlePagamentoFuncionarioFormGroup,
} from './controle-pagamento-funcionario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-controle-pagamento-funcionario-update',
  templateUrl: './controle-pagamento-funcionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ControlePagamentoFuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  controlePagamentoFuncionario: IControlePagamentoFuncionario | null = null;

  funcionariosSharedCollection: IFuncionario[] = [];

  editForm: ControlePagamentoFuncionarioFormGroup =
    this.controlePagamentoFuncionarioFormService.createControlePagamentoFuncionarioFormGroup();

  constructor(
    protected controlePagamentoFuncionarioService: ControlePagamentoFuncionarioService,
    protected controlePagamentoFuncionarioFormService: ControlePagamentoFuncionarioFormService,
    protected funcionarioService: FuncionarioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controlePagamentoFuncionario }) => {
      this.controlePagamentoFuncionario = controlePagamentoFuncionario;
      if (controlePagamentoFuncionario) {
        this.updateForm(controlePagamentoFuncionario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controlePagamentoFuncionario = this.controlePagamentoFuncionarioFormService.getControlePagamentoFuncionario(this.editForm);
    if (controlePagamentoFuncionario.id !== null) {
      this.subscribeToSaveResponse(this.controlePagamentoFuncionarioService.update(controlePagamentoFuncionario));
    } else {
      this.subscribeToSaveResponse(this.controlePagamentoFuncionarioService.create(controlePagamentoFuncionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControlePagamentoFuncionario>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(controlePagamentoFuncionario: IControlePagamentoFuncionario): void {
    this.controlePagamentoFuncionario = controlePagamentoFuncionario;
    this.controlePagamentoFuncionarioFormService.resetForm(this.editForm, controlePagamentoFuncionario);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      ...(controlePagamentoFuncionario.funcionarios ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
            funcionarios,
            ...(this.controlePagamentoFuncionario?.funcionarios ?? []),
          ),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
