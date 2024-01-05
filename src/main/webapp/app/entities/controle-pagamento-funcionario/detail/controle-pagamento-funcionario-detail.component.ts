import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IControlePagamentoFuncionario } from '../controle-pagamento-funcionario.model';

@Component({
  standalone: true,
  selector: 'jhi-controle-pagamento-funcionario-detail',
  templateUrl: './controle-pagamento-funcionario-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ControlePagamentoFuncionarioDetailComponent {
  @Input() controlePagamentoFuncionario: IControlePagamentoFuncionario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
