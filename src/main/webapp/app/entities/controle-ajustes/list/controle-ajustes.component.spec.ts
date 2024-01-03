import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ControleAjustesService } from '../service/controle-ajustes.service';

import { ControleAjustesComponent } from './controle-ajustes.component';

describe('ControleAjustes Management Component', () => {
  let comp: ControleAjustesComponent;
  let fixture: ComponentFixture<ControleAjustesComponent>;
  let service: ControleAjustesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'controle-ajustes', component: ControleAjustesComponent }]),
        HttpClientTestingModule,
        ControleAjustesComponent,
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
      .overrideTemplate(ControleAjustesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControleAjustesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ControleAjustesService);

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
    expect(comp.controleAjustes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to controleAjustesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getControleAjustesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getControleAjustesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
