import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IControleFrequencia } from '../controle-frequencia.model';

@Component({
  standalone: true,
  selector: 'jhi-controle-frequencia-detail',
  templateUrl: './controle-frequencia-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ControleFrequenciaDetailComponent {
  @Input() controleFrequencia: IControleFrequencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
