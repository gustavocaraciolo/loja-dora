import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleFrequenciaService } from '../service/controle-frequencia.service';

import { ControleFrequenciaComponent } from './controle-frequencia.component';

describe('ControleFrequencia Management Component', () => {
  let comp: ControleFrequenciaComponent;
  let fixture: ComponentFixture<ControleFrequenciaComponent>;
  let service: ControleFrequenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'controle-frequencia', component: ControleFrequenciaComponent }]),
        HttpClientTestingModule,
        ControleFrequenciaComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ControleFrequenciaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleFrequenciaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ControleFrequenciaService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.controleFrequencias?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to controleFrequenciaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getControleFrequenciaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getControleFrequenciaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
