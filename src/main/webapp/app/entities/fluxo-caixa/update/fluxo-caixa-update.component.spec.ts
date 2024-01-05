import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEntidades } from 'app/entities/entidades/entidades.model';
import { EntidadesService } from 'app/entities/entidades/service/entidades.service';
import { FluxoCaixaService } from '../service/fluxo-caixa.service';
import { IFluxoCaixa } from '../fluxo-caixa.model';
import { FluxoCaixaFormService } from './fluxo-caixa-form.service';

import { FluxoCaixaUpdateComponent } from './fluxo-caixa-update.component';

describe('FluxoCaixa Management Update Component', () => {
  let comp: FluxoCaixaUpdateComponent;
  let fixture: ComponentFixture<FluxoCaixaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fluxoCaixaFormService: FluxoCaixaFormService;
  let fluxoCaixaService: FluxoCaixaService;
  let entidadesService: EntidadesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), FluxoCaixaUpdateComponent],
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
      .overrideTemplate(FluxoCaixaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FluxoCaixaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fluxoCaixaFormService = TestBed.inject(FluxoCaixaFormService);
    fluxoCaixaService = TestBed.inject(FluxoCaixaService);
    entidadesService = TestBed.inject(EntidadesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Entidades query and add missing value', () => {
      const fluxoCaixa: IFluxoCaixa = { id: 456 };
      const entidades: IEntidades = { id: 14718 };
      fluxoCaixa.entidades = entidades;

      const entidadesCollection: IEntidades[] = [{ id: 22052 }];
      jest.spyOn(entidadesService, 'query').mockReturnValue(of(new HttpResponse({ body: entidadesCollection })));
      const additionalEntidades = [entidades];
      const expectedCollection: IEntidades[] = [...additionalEntidades, ...entidadesCollection];
      jest.spyOn(entidadesService, 'addEntidadesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fluxoCaixa });
      comp.ngOnInit();

      expect(entidadesService.query).toHaveBeenCalled();
      expect(entidadesService.addEntidadesToCollectionIfMissing).toHaveBeenCalledWith(
        entidadesCollection,
        ...additionalEntidades.map(expect.objectContaining),
      );
      expect(comp.entidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fluxoCaixa: IFluxoCaixa = { id: 456 };
      const entidades: IEntidades = { id: 30397 };
      fluxoCaixa.entidades = entidades;

      activatedRoute.data = of({ fluxoCaixa });
      comp.ngOnInit();

      expect(comp.entidadesSharedCollection).toContain(entidades);
      expect(comp.fluxoCaixa).toEqual(fluxoCaixa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoCaixa>>();
      const fluxoCaixa = { id: 123 };
      jest.spyOn(fluxoCaixaFormService, 'getFluxoCaixa').mockReturnValue(fluxoCaixa);
      jest.spyOn(fluxoCaixaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoCaixa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fluxoCaixa }));
      saveSubject.complete();

      // THEN
      expect(fluxoCaixaFormService.getFluxoCaixa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fluxoCaixaService.update).toHaveBeenCalledWith(expect.objectContaining(fluxoCaixa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoCaixa>>();
      const fluxoCaixa = { id: 123 };
      jest.spyOn(fluxoCaixaFormService, 'getFluxoCaixa').mockReturnValue({ id: null });
      jest.spyOn(fluxoCaixaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoCaixa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fluxoCaixa }));
      saveSubject.complete();

      // THEN
      expect(fluxoCaixaFormService.getFluxoCaixa).toHaveBeenCalled();
      expect(fluxoCaixaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoCaixa>>();
      const fluxoCaixa = { id: 123 };
      jest.spyOn(fluxoCaixaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoCaixa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fluxoCaixaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEntidades', () => {
      it('Should forward to entidadesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(entidadesService, 'compareEntidades');
        comp.compareEntidades(entity, entity2);
        expect(entidadesService.compareEntidades).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
