import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { ControleEntregasService } from '../service/controle-entregas.service';
import { IControleEntregas } from '../controle-entregas.model';
import { ControleEntregasFormService } from './controle-entregas-form.service';

import { ControleEntregasUpdateComponent } from './controle-entregas-update.component';

describe('ControleEntregas Management Update Component', () => {
  let comp: ControleEntregasUpdateComponent;
  let fixture: ComponentFixture<ControleEntregasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let controleEntregasFormService: ControleEntregasFormService;
  let controleEntregasService: ControleEntregasService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ControleEntregasUpdateComponent],
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
      .overrideTemplate(ControleEntregasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleEntregasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    controleEntregasFormService = TestBed.inject(ControleEntregasFormService);
    controleEntregasService = TestBed.inject(ControleEntregasService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const controleEntregas: IControleEntregas = { id: 456 };
      const funcionario: IFuncionario = { id: 19558 };
      controleEntregas.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 11196 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ controleEntregas });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const controleEntregas: IControleEntregas = { id: 456 };
      const funcionario: IFuncionario = { id: 24783 };
      controleEntregas.funcionario = funcionario;

      activatedRoute.data = of({ controleEntregas });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.controleEntregas).toEqual(controleEntregas);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleEntregas>>();
      const controleEntregas = { id: 123 };
      jest.spyOn(controleEntregasFormService, 'getControleEntregas').mockReturnValue(controleEntregas);
      jest.spyOn(controleEntregasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleEntregas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleEntregas }));
      saveSubject.complete();

      // THEN
      expect(controleEntregasFormService.getControleEntregas).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(controleEntregasService.update).toHaveBeenCalledWith(expect.objectContaining(controleEntregas));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleEntregas>>();
      const controleEntregas = { id: 123 };
      jest.spyOn(controleEntregasFormService, 'getControleEntregas').mockReturnValue({ id: null });
      jest.spyOn(controleEntregasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleEntregas: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleEntregas }));
      saveSubject.complete();

      // THEN
      expect(controleEntregasFormService.getControleEntregas).toHaveBeenCalled();
      expect(controleEntregasService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleEntregas>>();
      const controleEntregas = { id: 123 };
      jest.spyOn(controleEntregasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleEntregas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(controleEntregasService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFuncionario', () => {
      it('Should forward to funcionarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(funcionarioService, 'compareFuncionario');
        comp.compareFuncionario(entity, entity2);
        expect(funcionarioService.compareFuncionario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
