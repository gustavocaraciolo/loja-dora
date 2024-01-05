import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControlePagamentoFuncionarioDetailComponent } from './controle-pagamento-funcionario-detail.component';

describe('ControlePagamentoFuncionario Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControlePagamentoFuncionarioDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ControlePagamentoFuncionarioDetailComponent,
              resolve: { controlePagamentoFuncionario: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ControlePagamentoFuncionarioDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load controlePagamentoFuncionario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ControlePagamentoFuncionarioDetailComponent);

      // THEN
      expect(instance.controlePagamentoFuncionario).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
