import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IControlePagamentoFuncionario } from '../controle-pagamento-funcionario.model';
import { ControlePagamentoFuncionarioService } from '../service/controle-pagamento-funcionario.service';

@Component({
  standalone: true,
  templateUrl: './controle-pagamento-funcionario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ControlePagamentoFuncionarioDeleteDialogComponent {
  controlePagamentoFuncionario?: IControlePagamentoFuncionario;

  constructor(
    protected controlePagamentoFuncionarioService: ControlePagamentoFuncionarioService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.controlePagamentoFuncionarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
