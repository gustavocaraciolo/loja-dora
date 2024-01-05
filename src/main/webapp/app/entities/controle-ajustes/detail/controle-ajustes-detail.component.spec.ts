import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleAjustesDetailComponent } from './controle-ajustes-detail.component';

describe('ControleAjustes Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControleAjustesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ControleAjustesDetailComponent,
              resolve: { controleAjustes: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ControleAjustesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load controleAjustes on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ControleAjustesDetailComponent);

      // THEN
      expect(instance.controleAjustes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
