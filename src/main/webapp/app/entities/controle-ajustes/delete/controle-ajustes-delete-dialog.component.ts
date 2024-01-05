import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IControleAjustes } from '../controle-ajustes.model';
import { ControleAjustesService } from '../service/controle-ajustes.service';

@Component({
  standalone: true,
  templateUrl: './controle-ajustes-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ControleAjustesDeleteDialogComponent {
  controleAjustes?: IControleAjustes;

  constructor(
    protected controleAjustesService: ControleAjustesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.controleAjustesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
