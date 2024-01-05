import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleEntregasDetailComponent } from './controle-entregas-detail.component';

describe('ControleEntregas Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControleEntregasDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ControleEntregasDetailComponent,
              resolve: { controleEntregas: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ControleEntregasDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load controleEntregas on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ControleEntregasDetailComponent);

      // THEN
      expect(instance.controleEntregas).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
