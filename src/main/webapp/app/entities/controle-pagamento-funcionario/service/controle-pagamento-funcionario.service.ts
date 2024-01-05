import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IControlePagamentoFuncionario, NewControlePagamentoFuncionario } from '../controle-pagamento-funcionario.model';

export type PartialUpdateControlePagamentoFuncionario = Partial<IControlePagamentoFuncionario> & Pick<IControlePagamentoFuncionario, 'id'>;

type RestOf<T extends IControlePagamentoFuncionario | NewControlePagamentoFuncionario> = Omit<T, 'data'> & {
  data?: string | null;
};

export type RestControlePagamentoFuncionario = RestOf<IControlePagamentoFuncionario>;

export type NewRestControlePagamentoFuncionario = RestOf<NewControlePagamentoFuncionario>;

export type PartialUpdateRestControlePagamentoFuncionario = RestOf<PartialUpdateControlePagamentoFuncionario>;

export type EntityResponseType = HttpResponse<IControlePagamentoFuncionario>;
export type EntityArrayResponseType = HttpResponse<IControlePagamentoFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class ControlePagamentoFuncionarioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/controle-pagamento-funcionarios');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(controlePagamentoFuncionario: NewControlePagamentoFuncionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controlePagamentoFuncionario);
    return this.http
      .post<RestControlePagamentoFuncionario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(controlePagamentoFuncionario: IControlePagamentoFuncionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controlePagamentoFuncionario);
    return this.http
      .put<RestControlePagamentoFuncionario>(
        `${this.resourceUrl}/${this.getControlePagamentoFuncionarioIdentifier(controlePagamentoFuncionario)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(controlePagamentoFuncionario: PartialUpdateControlePagamentoFuncionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controlePagamentoFuncionario);
    return this.http
      .patch<RestControlePagamentoFuncionario>(
        `${this.resourceUrl}/${this.getControlePagamentoFuncionarioIdentifier(controlePagamentoFuncionario)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestControlePagamentoFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestControlePagamentoFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getControlePagamentoFuncionarioIdentifier(controlePagamentoFuncionario: Pick<IControlePagamentoFuncionario, 'id'>): number {
    return controlePagamentoFuncionario.id;
  }

  compareControlePagamentoFuncionario(
    o1: Pick<IControlePagamentoFuncionario, 'id'> | null,
    o2: Pick<IControlePagamentoFuncionario, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getControlePagamentoFuncionarioIdentifier(o1) === this.getControlePagamentoFuncionarioIdentifier(o2) : o1 === o2;
  }

  addControlePagamentoFuncionarioToCollectionIfMissing<Type extends Pick<IControlePagamentoFuncionario, 'id'>>(
    controlePagamentoFuncionarioCollection: Type[],
    ...controlePagamentoFuncionariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const controlePagamentoFuncionarios: Type[] = controlePagamentoFuncionariosToCheck.filter(isPresent);
    if (controlePagamentoFuncionarios.length > 0) {
      const controlePagamentoFuncionarioCollectionIdentifiers = controlePagamentoFuncionarioCollection.map(
        controlePagamentoFuncionarioItem => this.getControlePagamentoFuncionarioIdentifier(controlePagamentoFuncionarioItem)!,
      );
      const controlePagamentoFuncionariosToAdd = controlePagamentoFuncionarios.filter(controlePagamentoFuncionarioItem => {
        const controlePagamentoFuncionarioIdentifier = this.getControlePagamentoFuncionarioIdentifier(controlePagamentoFuncionarioItem);
        if (controlePagamentoFuncionarioCollectionIdentifiers.includes(controlePagamentoFuncionarioIdentifier)) {
          return false;
        }
        controlePagamentoFuncionarioCollectionIdentifiers.push(controlePagamentoFuncionarioIdentifier);
        return true;
      });
      return [...controlePagamentoFuncionariosToAdd, ...controlePagamentoFuncionarioCollection];
    }
    return controlePagamentoFuncionarioCollection;
  }

  protected convertDateFromClient<
    T extends IControlePagamentoFuncionario | NewControlePagamentoFuncionario | PartialUpdateControlePagamentoFuncionario,
  >(controlePagamentoFuncionario: T): RestOf<T> {
    return {
      ...controlePagamentoFuncionario,
      data: controlePagamentoFuncionario.data?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restControlePagamentoFuncionario: RestControlePagamentoFuncionario): IControlePagamentoFuncionario {
    return {
      ...restControlePagamentoFuncionario,
      data: restControlePagamentoFuncionario.data ? dayjs(restControlePagamentoFuncionario.data) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestControlePagamentoFuncionario>): HttpResponse<IControlePagamentoFuncionario> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestControlePagamentoFuncionario[]>,
  ): HttpResponse<IControlePagamentoFuncionario[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
