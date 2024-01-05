import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntidades } from '../entidades.model';
import { EntidadesService } from '../service/entidades.service';
import { EntidadesFormService, EntidadesFormGroup } from './entidades-form.service';

@Component({
  standalone: true,
  selector: 'jhi-entidades-update',
  templateUrl: './entidades-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EntidadesUpdateComponent implements OnInit {
  isSaving = false;
  entidades: IEntidades | null = null;

  editForm: EntidadesFormGroup = this.entidadesFormService.createEntidadesFormGroup();

  constructor(
    protected entidadesService: EntidadesService,
    protected entidadesFormService: EntidadesFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entidades }) => {
      this.entidades = entidades;
      if (entidades) {
        this.updateForm(entidades);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entidades = this.entidadesFormService.getEntidades(this.editForm);
    if (entidades.id !== null) {
      this.subscribeToSaveResponse(this.entidadesService.update(entidades));
    } else {
      this.subscribeToSaveResponse(this.entidadesService.create(entidades));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntidades>>): void {
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

  protected updateForm(entidades: IEntidades): void {
    this.entidades = entidades;
    this.entidadesFormService.resetForm(this.editForm, entidades);
  }
}
