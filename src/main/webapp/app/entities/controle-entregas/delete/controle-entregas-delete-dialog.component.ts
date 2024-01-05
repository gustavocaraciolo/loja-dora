import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IControleEntregas } from '../controle-entregas.model';
import { ControleEntregasService } from '../service/controle-entregas.service';

@Component({
  standalone: true,
  templateUrl: './controle-entregas-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ControleEntregasDeleteDialogComponent {
  controleEntregas?: IControleEntregas;

  constructor(
    protected controleEntregasService: ControleEntregasService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.controleEntregasService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
