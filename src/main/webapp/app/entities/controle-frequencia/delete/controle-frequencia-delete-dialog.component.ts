import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IControleFrequencia } from '../controle-frequencia.model';
import { ControleFrequenciaService } from '../service/controle-frequencia.service';

@Component({
  standalone: true,
  templateUrl: './controle-frequencia-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ControleFrequenciaDeleteDialogComponent {
  controleFrequencia?: IControleFrequencia;

  constructor(
    protected controleFrequenciaService: ControleFrequenciaService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.controleFrequenciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
