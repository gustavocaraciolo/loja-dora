import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFluxoCaixa } from '../fluxo-caixa.model';
import { FluxoCaixaService } from '../service/fluxo-caixa.service';

@Component({
  standalone: true,
  templateUrl: './fluxo-caixa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FluxoCaixaDeleteDialogComponent {
  fluxoCaixa?: IFluxoCaixa;

  constructor(
    protected fluxoCaixaService: FluxoCaixaService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fluxoCaixaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
