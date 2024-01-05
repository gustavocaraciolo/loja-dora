import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { ControleAjustesService } from '../service/controle-ajustes.service';
import { IControleAjustes } from '../controle-ajustes.model';
import { ControleAjustesFormService } from './controle-ajustes-form.service';

import { ControleAjustesUpdateComponent } from './controle-ajustes-update.component';

describe('ControleAjustes Management Update Component', () => {
  let comp: ControleAjustesUpdateComponent;
  let fixture: ComponentFixture<ControleAjustesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let controleAjustesFormService: ControleAjustesFormService;
  let controleAjustesService: ControleAjustesService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ControleAjustesUpdateComponent],
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
      .overrideTemplate(ControleAjustesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleAjustesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    controleAjustesFormService = TestBed.inject(ControleAjustesFormService);
    controleAjustesService = TestBed.inject(ControleAjustesService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const controleAjustes: IControleAjustes = { id: 456 };
      const funcionarios: IFuncionario[] = [{ id: 9552 }];
      controleAjustes.funcionarios = funcionarios;

      const funcionarioCollection: IFuncionario[] = [{ id: 4671 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [...funcionarios];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ controleAjustes });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const controleAjustes: IControleAjustes = { id: 456 };
      const funcionario: IFuncionario = { id: 5229 };
      controleAjustes.funcionarios = [funcionario];

      activatedRoute.data = of({ controleAjustes });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.controleAjustes).toEqual(controleAjustes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleAjustes>>();
      const controleAjustes = { id: 123 };
      jest.spyOn(controleAjustesFormService, 'getControleAjustes').mockReturnValue(controleAjustes);
      jest.spyOn(controleAjustesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleAjustes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleAjustes }));
      saveSubject.complete();

      // THEN
      expect(controleAjustesFormService.getControleAjustes).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(controleAjustesService.update).toHaveBeenCalledWith(expect.objectContaining(controleAjustes));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleAjustes>>();
      const controleAjustes = { id: 123 };
      jest.spyOn(controleAjustesFormService, 'getControleAjustes').mockReturnValue({ id: null });
      jest.spyOn(controleAjustesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleAjustes: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleAjustes }));
      saveSubject.complete();

      // THEN
      expect(controleAjustesFormService.getControleAjustes).toHaveBeenCalled();
      expect(controleAjustesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleAjustes>>();
      const controleAjustes = { id: 123 };
      jest.spyOn(controleAjustesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleAjustes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(controleAjustesService.update).toHaveBeenCalled();
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
