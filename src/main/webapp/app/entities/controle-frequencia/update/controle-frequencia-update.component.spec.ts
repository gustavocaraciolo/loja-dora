import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { ControleFrequenciaService } from '../service/controle-frequencia.service';
import { IControleFrequencia } from '../controle-frequencia.model';
import { ControleFrequenciaFormService } from './controle-frequencia-form.service';

import { ControleFrequenciaUpdateComponent } from './controle-frequencia-update.component';

describe('ControleFrequencia Management Update Component', () => {
  let comp: ControleFrequenciaUpdateComponent;
  let fixture: ComponentFixture<ControleFrequenciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let controleFrequenciaFormService: ControleFrequenciaFormService;
  let controleFrequenciaService: ControleFrequenciaService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ControleFrequenciaUpdateComponent],
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
      .overrideTemplate(ControleFrequenciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleFrequenciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    controleFrequenciaFormService = TestBed.inject(ControleFrequenciaFormService);
    controleFrequenciaService = TestBed.inject(ControleFrequenciaService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const controleFrequencia: IControleFrequencia = { id: 456 };
      const funcionario: IFuncionario = { id: 20156 };
      controleFrequencia.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 27388 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ controleFrequencia });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const controleFrequencia: IControleFrequencia = { id: 456 };
      const funcionario: IFuncionario = { id: 26951 };
      controleFrequencia.funcionario = funcionario;

      activatedRoute.data = of({ controleFrequencia });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.controleFrequencia).toEqual(controleFrequencia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleFrequencia>>();
      const controleFrequencia = { id: 123 };
      jest.spyOn(controleFrequenciaFormService, 'getControleFrequencia').mockReturnValue(controleFrequencia);
      jest.spyOn(controleFrequenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleFrequencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleFrequencia }));
      saveSubject.complete();

      // THEN
      expect(controleFrequenciaFormService.getControleFrequencia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(controleFrequenciaService.update).toHaveBeenCalledWith(expect.objectContaining(controleFrequencia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleFrequencia>>();
      const controleFrequencia = { id: 123 };
      jest.spyOn(controleFrequenciaFormService, 'getControleFrequencia').mockReturnValue({ id: null });
      jest.spyOn(controleFrequenciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleFrequencia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controleFrequencia }));
      saveSubject.complete();

      // THEN
      expect(controleFrequenciaFormService.getControleFrequencia).toHaveBeenCalled();
      expect(controleFrequenciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControleFrequencia>>();
      const controleFrequencia = { id: 123 };
      jest.spyOn(controleFrequenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controleFrequencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(controleFrequenciaService.update).toHaveBeenCalled();
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
