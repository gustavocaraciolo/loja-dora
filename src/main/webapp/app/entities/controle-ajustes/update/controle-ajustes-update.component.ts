import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { Receita } from 'app/entities/enumerations/receita.model';
import { ControleAjustesService } from '../service/controle-ajustes.service';
import { IControleAjustes } from '../controle-ajustes.model';
import { ControleAjustesFormService, ControleAjustesFormGroup } from './controle-ajustes-form.service';

@Component({
  standalone: true,
  selector: 'jhi-controle-ajustes-update',
  templateUrl: './controle-ajustes-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ControleAjustesUpdateComponent implements OnInit {
  isSaving = false;
  controleAjustes: IControleAjustes | null = null;
  receitaValues = Object.keys(Receita);

  funcionariosSharedCollection: IFuncionario[] = [];

  editForm: ControleAjustesFormGroup = this.controleAjustesFormService.createControleAjustesFormGroup();

  constructor(
    protected controleAjustesService: ControleAjustesService,
    protected controleAjustesFormService: ControleAjustesFormService,
    protected funcionarioService: FuncionarioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controleAjustes }) => {
      this.controleAjustes = controleAjustes;
      if (controleAjustes) {
        this.updateForm(controleAjustes);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controleAjustes = this.controleAjustesFormService.getControleAjustes(this.editForm);
    if (controleAjustes.id !== null) {
      this.subscribeToSaveResponse(this.controleAjustesService.update(controleAjustes));
    } else {
      this.subscribeToSaveResponse(this.controleAjustesService.create(controleAjustes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControleAjustes>>): void {
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

  protected updateForm(controleAjustes: IControleAjustes): void {
    this.controleAjustes = controleAjustes;
    this.controleAjustesFormService.resetForm(this.editForm, controleAjustes);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      controleAjustes.funcionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.controleAjustes?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
