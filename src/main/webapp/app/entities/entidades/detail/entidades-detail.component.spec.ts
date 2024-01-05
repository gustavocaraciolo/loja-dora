import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EntidadesDetailComponent } from './entidades-detail.component';

describe('Entidades Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntidadesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EntidadesDetailComponent,
              resolve: { entidades: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EntidadesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load entidades on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EntidadesDetailComponent);

      // THEN
      expect(instance.entidades).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
