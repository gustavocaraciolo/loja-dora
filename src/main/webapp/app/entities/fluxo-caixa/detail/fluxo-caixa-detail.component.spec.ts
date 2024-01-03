import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FluxoCaixaDetailComponent } from './fluxo-caixa-detail.component';

describe('FluxoCaixa Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FluxoCaixaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FluxoCaixaDetailComponent,
              resolve: { fluxoCaixa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FluxoCaixaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load fluxoCaixa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FluxoCaixaDetailComponent);

      // THEN
      expect(instance.fluxoCaixa).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
