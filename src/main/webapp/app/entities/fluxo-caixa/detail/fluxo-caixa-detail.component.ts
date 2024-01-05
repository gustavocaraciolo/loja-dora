import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFluxoCaixa } from '../fluxo-caixa.model';

@Component({
  standalone: true,
  selector: 'jhi-fluxo-caixa-detail',
  templateUrl: './fluxo-caixa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FluxoCaixaDetailComponent {
  @Input() fluxoCaixa: IFluxoCaixa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
