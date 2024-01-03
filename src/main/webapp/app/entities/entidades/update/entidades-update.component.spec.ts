import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EntidadesService } from '../service/entidades.service';
import { IEntidades } from '../entidades.model';
import { EntidadesFormService } from './entidades-form.service';

import { EntidadesUpdateComponent } from './entidades-update.component';

describe('Entidades Management Update Component', () => {
  let comp: EntidadesUpdateComponent;
  let fixture: ComponentFixture<EntidadesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entidadesFormService: EntidadesFormService;
  let entidadesService: EntidadesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EntidadesUpdateComponent],
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
      .overrideTemplate(EntidadesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntidadesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entidadesFormService = TestBed.inject(EntidadesFormService);
    entidadesService = TestBed.inject(EntidadesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const entidades: IEntidades = { id: 456 };

      activatedRoute.data = of({ entidades });
      comp.ngOnInit();

      expect(comp.entidades).toEqual(entidades);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntidades>>();
      const entidades = { id: 123 };
      jest.spyOn(entidadesFormService, 'getEntidades').mockReturnValue(entidades);
      jest.spyOn(entidadesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entidades });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entidades }));
      saveSubject.complete();

      // THEN
      expect(entidadesFormService.getEntidades).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(entidadesService.update).toHaveBeenCalledWith(expect.objectContaining(entidades));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntidades>>();
      const entidades = { id: 123 };
      jest.spyOn(entidadesFormService, 'getEntidades').mockReturnValue({ id: null });
      jest.spyOn(entidadesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entidades: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entidades }));
      saveSubject.complete();

      // THEN
      expect(entidadesFormService.getEntidades).toHaveBeenCalled();
      expect(entidadesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntidades>>();
      const entidades = { id: 123 };
      jest.spyOn(entidadesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entidades });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entidadesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
