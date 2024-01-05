import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ControleDiarioService } from '../service/controle-diario.service';
import { IControleDiario } from '../controle-diario.model';
import { ControleDiarioFormService } from './controle-diario-form.service';

import { ControleDiarioUpdateComponent } from './controle-diario-update.component';

describe('ControleDiario Management Update Component', () => {
  let comp: ControleDiarioUpdateComponent;
  let fixture: ComponentFixture<ControleDiarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let controleDiarioFormService: ControleDiarioFormService;
  let controleDiarioService: ControleDiarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ControleDiarioUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ControleDiarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleDiarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    controleDiarioFormService = TestBed.inject(ControleDiarioFormService);
    controleDiarioService = TestBed.inject(ControleDiarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const controleDiario: IControleDiario = { id: 456 };

      activatedRoute.data = of({ controleDiario });
      comp.ngOnInit();

      expect(comp.controleDiario).toEqual(controleDiario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleDiario>>();
      const controleDiario = { id: 123 };
      jest.spyOn(controleDiarioFormService, 'getControleDiario').mockReturnValue(controleDiario);
      jest.spyOn(controleDiarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleDiario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleDiario }));
      saveSubject.complete();

      // THEN
      expect(controleDiarioFormService.getControleDiario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(controleDiarioService.update).toHaveBeenCalledWith(expect.objectContaining(controleDiario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleDiario>>();
      const controleDiario = { id: 123 };
      jest.spyOn(controleDiarioFormService, 'getControleDiario').mockReturnValue({ id: null });
      jest.spyOn(controleDiarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleDiario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleDiario }));
      saveSubject.complete();

      // THEN
      expect(controleDiarioFormService.getControleDiario).toHaveBeenCalled();
      expect(controleDiarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleDiario>>();
      const controleDiario = { id: 123 };
      jest.spyOn(controleDiarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleDiario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(controleDiarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
