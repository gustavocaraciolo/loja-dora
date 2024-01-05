import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { ControlePagamentoFuncionarioService } from '../service/controle-pagamento-funcionario.service';
import { IControlePagamentoFuncionario } from '../controle-pagamento-funcionario.model';
import { ControlePagamentoFuncionarioFormService } from './controle-pagamento-funcionario-form.service';

import { ControlePagamentoFuncionarioUpdateComponent } from './controle-pagamento-funcionario-update.component';

describe('ControlePagamentoFuncionario Management Update Component', () => {
  let comp: ControlePagamentoFuncionarioUpdateComponent;
  let fixture: ComponentFixture<ControlePagamentoFuncionarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let controlePagamentoFuncionarioFormService: ControlePagamentoFuncionarioFormService;
  let controlePagamentoFuncionarioService: ControlePagamentoFuncionarioService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ControlePagamentoFuncionarioUpdateComponent],
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
      .overrideTemplate(ControlePagamentoFuncionarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControlePagamentoFuncionarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    controlePagamentoFuncionarioFormService = TestBed.inject(ControlePagamentoFuncionarioFormService);
    controlePagamentoFuncionarioService = TestBed.inject(ControlePagamentoFuncionarioService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const controlePagamentoFuncionario: IControlePagamentoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 12233 };
      controlePagamentoFuncionario.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 28584 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ controlePagamentoFuncionario });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const controlePagamentoFuncionario: IControlePagamentoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 29075 };
      controlePagamentoFuncionario.funcionario = funcionario;

      activatedRoute.data = of({ controlePagamentoFuncionario });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.controlePagamentoFuncionario).toEqual(controlePagamentoFuncionario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControlePagamentoFuncionario>>();
      const controlePagamentoFuncionario = { id: 123 };
      jest.spyOn(controlePagamentoFuncionarioFormService, 'getControlePagamentoFuncionario').mockReturnValue(controlePagamentoFuncionario);
      jest.spyOn(controlePagamentoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controlePagamentoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controlePagamentoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(controlePagamentoFuncionarioFormService.getControlePagamentoFuncionario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(controlePagamentoFuncionarioService.update).toHaveBeenCalledWith(expect.objectContaining(controlePagamentoFuncionario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControlePagamentoFuncionario>>();
      const controlePagamentoFuncionario = { id: 123 };
      jest.spyOn(controlePagamentoFuncionarioFormService, 'getControlePagamentoFuncionario').mockReturnValue({ id: null });
      jest.spyOn(controlePagamentoFuncionarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controlePagamentoFuncionario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controlePagamentoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(controlePagamentoFuncionarioFormService.getControlePagamentoFuncionario).toHaveBeenCalled();
      expect(controlePagamentoFuncionarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControlePagamentoFuncionario>>();
      const controlePagamentoFuncionario = { id: 123 };
      jest.spyOn(controlePagamentoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controlePagamentoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(controlePagamentoFuncionarioService.update).toHaveBeenCalled();
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
