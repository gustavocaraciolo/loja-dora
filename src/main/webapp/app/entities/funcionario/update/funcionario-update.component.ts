import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from '../funcionario.model';
import { FuncionarioService } from '../service/funcionario.service';
import { FuncionarioFormService, FuncionarioFormGroup } from './funcionario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-funcionario-update',
  templateUrl: './funcionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  funcionario: IFuncionario | null = null;

  editForm: FuncionarioFormGroup = this.funcionarioFormService.createFuncionarioFormGroup();

  constructor(
    protected funcionarioService: FuncionarioService,
    protected funcionarioFormService: FuncionarioFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcionario }) => {
      this.funcionario = funcionario;
      if (funcionario) {
        this.updateForm(funcionario);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcionario = this.funcionarioFormService.getFuncionario(this.editForm);
    if (funcionario.id !== null) {
      this.subscribeToSaveResponse(this.funcionarioService.update(funcionario));
    } else {
      this.subscribeToSaveResponse(this.funcionarioService.create(funcionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionario>>): void {
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

  protected updateForm(funcionario: IFuncionario): void {
    this.funcionario = funcionario;
    this.funcionarioFormService.resetForm(this.editForm, funcionario);
  }
}
