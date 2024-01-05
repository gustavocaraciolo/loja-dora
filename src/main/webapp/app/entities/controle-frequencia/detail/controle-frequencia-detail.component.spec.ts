import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleFrequenciaDetailComponent } from './controle-frequencia-detail.component';

describe('ControleFrequencia Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControleFrequenciaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ControleFrequenciaDetailComponent,
              resolve: { controleFrequencia: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ControleFrequenciaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load controleFrequencia on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ControleFrequenciaDetailComponent);

      // THEN
      expect(instance.controleFrequencia).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
