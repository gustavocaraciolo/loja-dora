import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IControleDiario } from '../controle-diario.model';
import { ControleDiarioService } from '../service/controle-diario.service';

@Component({
  standalone: true,
  templateUrl: './controle-diario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ControleDiarioDeleteDialogComponent {
  controleDiario?: IControleDiario;

  constructor(
    protected controleDiarioService: ControleDiarioService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.controleDiarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
