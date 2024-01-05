import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntidades } from 'app/entities/entidades/entidades.model';
import { EntidadesService } from 'app/entities/entidades/service/entidades.service';
import { Banco } from 'app/entities/enumerations/banco.model';
import { FixoVariavel } from 'app/entities/enumerations/fixo-variavel.model';
import { EntradaSaida } from 'app/entities/enumerations/entrada-saida.model';
import { Pais } from 'app/entities/enumerations/pais.model';
import { FluxoCaixaService } from '../service/fluxo-caixa.service';
import { IFluxoCaixa } from '../fluxo-caixa.model';
import { FluxoCaixaFormService, FluxoCaixaFormGroup } from './fluxo-caixa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-fluxo-caixa-update',
  templateUrl: './fluxo-caixa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FluxoCaixaUpdateComponent implements OnInit {
  isSaving = false;
  fluxoCaixa: IFluxoCaixa | null = null;
  bancoValues = Object.keys(Banco);
  fixoVariavelValues = Object.keys(FixoVariavel);
  entradaSaidaValues = Object.keys(EntradaSaida);
  paisValues = Object.keys(Pais);

  entidadesSharedCollection: IEntidades[] = [];

  editForm: FluxoCaixaFormGroup = this.fluxoCaixaFormService.createFluxoCaixaFormGroup();

  constructor(
    protected fluxoCaixaService: FluxoCaixaService,
    protected fluxoCaixaFormService: FluxoCaixaFormService,
    protected entidadesService: EntidadesService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEntidades = (o1: IEntidades | null, o2: IEntidades | null): boolean => this.entidadesService.compareEntidades(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fluxoCaixa }) => {
      this.fluxoCaixa = fluxoCaixa;
      if (fluxoCaixa) {
        this.updateForm(fluxoCaixa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fluxoCaixa = this.fluxoCaixaFormService.getFluxoCaixa(this.editForm);
    if (fluxoCaixa.id !== null) {
      this.subscribeToSaveResponse(this.fluxoCaixaService.update(fluxoCaixa));
    } else {
      this.subscribeToSaveResponse(this.fluxoCaixaService.create(fluxoCaixa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFluxoCaixa>>): void {
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

  protected updateForm(fluxoCaixa: IFluxoCaixa): void {
    this.fluxoCaixa = fluxoCaixa;
    this.fluxoCaixaFormService.resetForm(this.editForm, fluxoCaixa);

    this.entidadesSharedCollection = this.entidadesService.addEntidadesToCollectionIfMissing<IEntidades>(
      this.entidadesSharedCollection,
      fluxoCaixa.entidades,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entidadesService
      .query()
      .pipe(map((res: HttpResponse<IEntidades[]>) => res.body ?? []))
      .pipe(
        map((entidades: IEntidades[]) =>
          this.entidadesService.addEntidadesToCollectionIfMissing<IEntidades>(entidades, this.fluxoCaixa?.entidades),
        ),
      )
      .subscribe((entidades: IEntidades[]) => (this.entidadesSharedCollection = entidades));
  }
}
