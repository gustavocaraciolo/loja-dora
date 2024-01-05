import { Component, LOCALE_ID, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { Receita } from 'app/entities/enumerations/receita.model';
import { ControleEntregasService } from '../service/controle-entregas.service';
import { IControleEntregas } from '../controle-entregas.model';
import { ControleEntregasFormService, ControleEntregasFormGroup } from './controle-entregas-form.service';
import localePt from '@angular/common/locales/pt';
import { registerLocaleData } from '@angular/common';
import { NgxCurrencyDirective } from 'ngx-currency';
import { NgbDateCustomParserFormatter } from '../../../shared/date/format-display-date-pt.pipe';
import { NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';

registerLocaleData(localePt);
@Component({
  standalone: true,
  selector: 'jhi-controle-entregas-update',
  templateUrl: './controle-entregas-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, NgxCurrencyDirective],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    { provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter },
  ],
})
export class ControleEntregasUpdateComponent implements OnInit {
  isSaving = false;
  controleEntregas: IControleEntregas | null = null;
  receitaValues = Object.keys(Receita);

  funcionariosSharedCollection: IFuncionario[] = [];

  editForm: ControleEntregasFormGroup = this.controleEntregasFormService.createControleEntregasFormGroup();

  constructor(
    protected controleEntregasService: ControleEntregasService,
    protected controleEntregasFormService: ControleEntregasFormService,
    protected funcionarioService: FuncionarioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controleEntregas }) => {
      this.controleEntregas = controleEntregas;
      if (controleEntregas) {
        this.updateForm(controleEntregas);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controleEntregas = this.controleEntregasFormService.getControleEntregas(this.editForm);
    if (controleEntregas.id !== null) {
      this.subscribeToSaveResponse(this.controleEntregasService.update(controleEntregas));
    } else {
      this.subscribeToSaveResponse(this.controleEntregasService.create(controleEntregas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControleEntregas>>): void {
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

  protected updateForm(controleEntregas: IControleEntregas): void {
    this.controleEntregas = controleEntregas;
    this.controleEntregasFormService.resetForm(this.editForm, controleEntregas);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      controleEntregas.funcionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.controleEntregas?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
