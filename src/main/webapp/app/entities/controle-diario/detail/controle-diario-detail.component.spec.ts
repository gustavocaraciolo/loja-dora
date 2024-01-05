import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleDiarioDetailComponent } from './controle-diario-detail.component';

describe('ControleDiario Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControleDiarioDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ControleDiarioDetailComponent,
              resolve: { controleDiario: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ControleDiarioDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load controleDiario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ControleDiarioDetailComponent);

      // THEN
      expect(instance.controleDiario).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
