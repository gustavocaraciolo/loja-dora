import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEntidades } from '../entidades.model';
import { EntidadesService } from '../service/entidades.service';

@Component({
  standalone: true,
  templateUrl: './entidades-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EntidadesDeleteDialogComponent {
  entidades?: IEntidades;

  constructor(
    protected entidadesService: EntidadesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entidadesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
