import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFluxoCaixa, NewFluxoCaixa } from '../fluxo-caixa.model';

export type PartialUpdateFluxoCaixa = Partial<IFluxoCaixa> & Pick<IFluxoCaixa, 'id'>;

type RestOf<T extends IFluxoCaixa | NewFluxoCaixa> = Omit<T, 'data'> & {
  data?: string | null;
};

export type RestFluxoCaixa = RestOf<IFluxoCaixa>;

export type NewRestFluxoCaixa = RestOf<NewFluxoCaixa>;

export type PartialUpdateRestFluxoCaixa = RestOf<PartialUpdateFluxoCaixa>;

export type EntityResponseType = HttpResponse<IFluxoCaixa>;
export type EntityArrayResponseType = HttpResponse<IFluxoCaixa[]>;

@Injectable({ providedIn: 'root' })
export class FluxoCaixaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fluxo-caixas');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(fluxoCaixa: NewFluxoCaixa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fluxoCaixa);
    return this.http
      .post<RestFluxoCaixa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(fluxoCaixa: IFluxoCaixa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fluxoCaixa);
    return this.http
      .put<RestFluxoCaixa>(`${this.resourceUrl}/${this.getFluxoCaixaIdentifier(fluxoCaixa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(fluxoCaixa: PartialUpdateFluxoCaixa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fluxoCaixa);
    return this.http
      .patch<RestFluxoCaixa>(`${this.resourceUrl}/${this.getFluxoCaixaIdentifier(fluxoCaixa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFluxoCaixa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFluxoCaixa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFluxoCaixaIdentifier(fluxoCaixa: Pick<IFluxoCaixa, 'id'>): number {
    return fluxoCaixa.id;
  }

  compareFluxoCaixa(o1: Pick<IFluxoCaixa, 'id'> | null, o2: Pick<IFluxoCaixa, 'id'> | null): boolean {
    return o1 && o2 ? this.getFluxoCaixaIdentifier(o1) === this.getFluxoCaixaIdentifier(o2) : o1 === o2;
  }

  addFluxoCaixaToCollectionIfMissing<Type extends Pick<IFluxoCaixa, 'id'>>(
    fluxoCaixaCollection: Type[],
    ...fluxoCaixasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fluxoCaixas: Type[] = fluxoCaixasToCheck.filter(isPresent);
    if (fluxoCaixas.length > 0) {
      const fluxoCaixaCollectionIdentifiers = fluxoCaixaCollection.map(fluxoCaixaItem => this.getFluxoCaixaIdentifier(fluxoCaixaItem)!);
      const fluxoCaixasToAdd = fluxoCaixas.filter(fluxoCaixaItem => {
        const fluxoCaixaIdentifier = this.getFluxoCaixaIdentifier(fluxoCaixaItem);
        if (fluxoCaixaCollectionIdentifiers.includes(fluxoCaixaIdentifier)) {
          return false;
        }
        fluxoCaixaCollectionIdentifiers.push(fluxoCaixaIdentifier);
        return true;
      });
      return [...fluxoCaixasToAdd, ...fluxoCaixaCollection];
    }
    return fluxoCaixaCollection;
  }

  protected convertDateFromClient<T extends IFluxoCaixa | NewFluxoCaixa | PartialUpdateFluxoCaixa>(fluxoCaixa: T): RestOf<T> {
    return {
      ...fluxoCaixa,
      data: fluxoCaixa.data?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFluxoCaixa: RestFluxoCaixa): IFluxoCaixa {
    return {
      ...restFluxoCaixa,
      data: restFluxoCaixa.data ? dayjs(restFluxoCaixa.data) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFluxoCaixa>): HttpResponse<IFluxoCaixa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFluxoCaixa[]>): HttpResponse<IFluxoCaixa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
