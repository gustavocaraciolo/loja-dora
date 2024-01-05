import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { FormasPagamento } from 'app/entities/enumerations/formas-pagamento.model';
import { Banco } from 'app/entities/enumerations/banco.model';
import { IControleDiario } from '../controle-diario.model';
import { ControleDiarioService } from '../service/controle-diario.service';
import { ControleDiarioFormService, ControleDiarioFormGroup } from './controle-diario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-controle-diario-update',
  templateUrl: './controle-diario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ControleDiarioUpdateComponent implements OnInit {
  isSaving = false;
  controleDiario: IControleDiario | null = null;
  formasPagamentoValues = Object.keys(FormasPagamento);
  bancoValues = Object.keys(Banco);

  editForm: ControleDiarioFormGroup = this.controleDiarioFormService.createControleDiarioFormGroup();

  constructor(
    protected controleDiarioService: ControleDiarioService,
    protected controleDiarioFormService: ControleDiarioFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controleDiario }) => {
      this.controleDiario = controleDiario;
      if (controleDiario) {
        this.updateForm(controleDiario);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controleDiario = this.controleDiarioFormService.getControleDiario(this.editForm);
    if (controleDiario.id !== null) {
      this.subscribeToSaveResponse(this.controleDiarioService.update(controleDiario));
    } else {
      this.subscribeToSaveResponse(this.controleDiarioService.create(controleDiario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControleDiario>>): void {
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

  protected updateForm(controleDiario: IControleDiario): void {
    this.controleDiario = controleDiario;
    this.controleDiarioFormService.resetForm(this.editForm, controleDiario);
  }
}
