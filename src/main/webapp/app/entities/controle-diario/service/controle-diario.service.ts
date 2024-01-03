import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IControleDiario, NewControleDiario } from '../controle-diario.model';

export type PartialUpdateControleDiario = Partial<IControleDiario> & Pick<IControleDiario, 'id'>;

type RestOf<T extends IControleDiario | NewControleDiario> = Omit<T, 'data'> & {
  data?: string | null;
};

export type RestControleDiario = RestOf<IControleDiario>;

export type NewRestControleDiario = RestOf<NewControleDiario>;

export type PartialUpdateRestControleDiario = RestOf<PartialUpdateControleDiario>;

export type EntityResponseType = HttpResponse<IControleDiario>;
export type EntityArrayResponseType = HttpResponse<IControleDiario[]>;

@Injectable({ providedIn: 'root' })
export class ControleDiarioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/controle-diarios');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(controleDiario: NewControleDiario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleDiario);
    return this.http
      .post<RestControleDiario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(controleDiario: IControleDiario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleDiario);
    return this.http
      .put<RestControleDiario>(`${this.resourceUrl}/${this.getControleDiarioIdentifier(controleDiario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(controleDiario: PartialUpdateControleDiario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleDiario);
    return this.http
      .patch<RestControleDiario>(`${this.resourceUrl}/${this.getControleDiarioIdentifier(controleDiario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestControleDiario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestControleDiario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getControleDiarioIdentifier(controleDiario: Pick<IControleDiario, 'id'>): number {
    return controleDiario.id;
  }

  compareControleDiario(o1: Pick<IControleDiario, 'id'> | null, o2: Pick<IControleDiario, 'id'> | null): boolean {
    return o1 && o2 ? this.getControleDiarioIdentifier(o1) === this.getControleDiarioIdentifier(o2) : o1 === o2;
  }

  addControleDiarioToCollectionIfMissing<Type extends Pick<IControleDiario, 'id'>>(
    controleDiarioCollection: Type[],
    ...controleDiariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const controleDiarios: Type[] = controleDiariosToCheck.filter(isPresent);
    if (controleDiarios.length > 0) {
      const controleDiarioCollectionIdentifiers = controleDiarioCollection.map(
        controleDiarioItem => this.getControleDiarioIdentifier(controleDiarioItem)!,
      );
      const controleDiariosToAdd = controleDiarios.filter(controleDiarioItem => {
        const controleDiarioIdentifier = this.getControleDiarioIdentifier(controleDiarioItem);
        if (controleDiarioCollectionIdentifiers.includes(controleDiarioIdentifier)) {
          return false;
        }
        controleDiarioCollectionIdentifiers.push(controleDiarioIdentifier);
        return true;
      });
      return [...controleDiariosToAdd, ...controleDiarioCollection];
    }
    return controleDiarioCollection;
  }

  protected convertDateFromClient<T extends IControleDiario | NewControleDiario | PartialUpdateControleDiario>(
    controleDiario: T,
  ): RestOf<T> {
    return {
      ...controleDiario,
      data: controleDiario.data?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restControleDiario: RestControleDiario): IControleDiario {
    return {
      ...restControleDiario,
      data: restControleDiario.data ? dayjs(restControleDiario.data) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestControleDiario>): HttpResponse<IControleDiario> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestControleDiario[]>): HttpResponse<IControleDiario[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
