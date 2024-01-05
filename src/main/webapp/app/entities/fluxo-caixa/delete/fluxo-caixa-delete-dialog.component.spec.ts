jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FluxoCaixaService } from '../service/fluxo-caixa.service';

import { FluxoCaixaDeleteDialogComponent } from './fluxo-caixa-delete-dialog.component';

describe('FluxoCaixa Management Delete Component', () => {
  let comp: FluxoCaixaDeleteDialogComponent;
  let fixture: ComponentFixture<FluxoCaixaDeleteDialogComponent>;
  let service: FluxoCaixaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FluxoCaixaDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(FluxoCaixaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FluxoCaixaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FluxoCaixaService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
