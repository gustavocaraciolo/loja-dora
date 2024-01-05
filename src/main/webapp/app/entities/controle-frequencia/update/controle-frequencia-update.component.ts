import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IControleFrequencia } from '../controle-frequencia.model';
import { ControleFrequenciaService } from '../service/controle-frequencia.service';
import { ControleFrequenciaFormService, ControleFrequenciaFormGroup } from './controle-frequencia-form.service';

@Component({
  standalone: true,
  selector: 'jhi-controle-frequencia-update',
  templateUrl: './controle-frequencia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ControleFrequenciaUpdateComponent implements OnInit {
  isSaving = false;
  controleFrequencia: IControleFrequencia | null = null;

  funcionariosSharedCollection: IFuncionario[] = [];

  editForm: ControleFrequenciaFormGroup = this.controleFrequenciaFormService.createControleFrequenciaFormGroup();

  constructor(
    protected controleFrequenciaService: ControleFrequenciaService,
    protected controleFrequenciaFormService: ControleFrequenciaFormService,
    protected funcionarioService: FuncionarioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controleFrequencia }) => {
      this.controleFrequencia = controleFrequencia;
      if (controleFrequencia) {
        this.updateForm(controleFrequencia);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controleFrequencia = this.controleFrequenciaFormService.getControleFrequencia(this.editForm);
    if (controleFrequencia.id !== null) {
      this.subscribeToSaveResponse(this.controleFrequenciaService.update(controleFrequencia));
    } else {
      this.subscribeToSaveResponse(this.controleFrequenciaService.create(controleFrequencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControleFrequencia>>): void {
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

  protected updateForm(controleFrequencia: IControleFrequencia): void {
    this.controleFrequencia = controleFrequencia;
    this.controleFrequenciaFormService.resetForm(this.editForm, controleFrequencia);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      controleFrequencia.funcionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.controleFrequencia?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
