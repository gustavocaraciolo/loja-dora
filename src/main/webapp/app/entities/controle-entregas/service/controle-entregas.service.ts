import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IControleEntregas, NewControleEntregas } from '../controle-entregas.model';

export type PartialUpdateControleEntregas = Partial<IControleEntregas> & Pick<IControleEntregas, 'id'>;

type RestOf<T extends IControleEntregas | NewControleEntregas> = Omit<T, 'data'> & {
  data?: string | null;
};

export type RestControleEntregas = RestOf<IControleEntregas>;

export type NewRestControleEntregas = RestOf<NewControleEntregas>;

export type PartialUpdateRestControleEntregas = RestOf<PartialUpdateControleEntregas>;

export type EntityResponseType = HttpResponse<IControleEntregas>;
export type EntityArrayResponseType = HttpResponse<IControleEntregas[]>;

@Injectable({ providedIn: 'root' })
export class ControleEntregasService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/controle-entregases');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(controleEntregas: NewControleEntregas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleEntregas);
    return this.http
      .post<RestControleEntregas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(controleEntregas: IControleEntregas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleEntregas);
    return this.http
      .put<RestControleEntregas>(`${this.resourceUrl}/${this.getControleEntregasIdentifier(controleEntregas)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(controleEntregas: PartialUpdateControleEntregas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleEntregas);
    return this.http
      .patch<RestControleEntregas>(`${this.resourceUrl}/${this.getControleEntregasIdentifier(controleEntregas)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestControleEntregas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestControleEntregas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getControleEntregasIdentifier(controleEntregas: Pick<IControleEntregas, 'id'>): number {
    return controleEntregas.id;
  }

  compareControleEntregas(o1: Pick<IControleEntregas, 'id'> | null, o2: Pick<IControleEntregas, 'id'> | null): boolean {
    return o1 && o2 ? this.getControleEntregasIdentifier(o1) === this.getControleEntregasIdentifier(o2) : o1 === o2;
  }

  addControleEntregasToCollectionIfMissing<Type extends Pick<IControleEntregas, 'id'>>(
    controleEntregasCollection: Type[],
    ...controleEntregasesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const controleEntregases: Type[] = controleEntregasesToCheck.filter(isPresent);
    if (controleEntregases.length > 0) {
      const controleEntregasCollectionIdentifiers = controleEntregasCollection.map(
        controleEntregasItem => this.getControleEntregasIdentifier(controleEntregasItem)!,
      );
      const controleEntregasesToAdd = controleEntregases.filter(controleEntregasItem => {
        const controleEntregasIdentifier = this.getControleEntregasIdentifier(controleEntregasItem);
        if (controleEntregasCollectionIdentifiers.includes(controleEntregasIdentifier)) {
          return false;
        }
        controleEntregasCollectionIdentifiers.push(controleEntregasIdentifier);
        return true;
      });
      return [...controleEntregasesToAdd, ...controleEntregasCollection];
    }
    return controleEntregasCollection;
  }

  protected convertDateFromClient<T extends IControleEntregas | NewControleEntregas | PartialUpdateControleEntregas>(
    controleEntregas: T,
  ): RestOf<T> {
    return {
      ...controleEntregas,
      data: controleEntregas.data?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restControleEntregas: RestControleEntregas): IControleEntregas {
    return {
      ...restControleEntregas,
      data: restControleEntregas.data ? dayjs(restControleEntregas.data) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestControleEntregas>): HttpResponse<IControleEntregas> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestControleEntregas[]>): HttpResponse<IControleEntregas[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
