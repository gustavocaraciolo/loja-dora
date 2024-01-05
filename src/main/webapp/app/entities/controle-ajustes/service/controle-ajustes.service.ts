import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IControleAjustes, NewControleAjustes } from '../controle-ajustes.model';

export type PartialUpdateControleAjustes = Partial<IControleAjustes> & Pick<IControleAjustes, 'id'>;

type RestOf<T extends IControleAjustes | NewControleAjustes> = Omit<T, 'dataEntrega' | 'dataRecebimento'> & {
  dataEntrega?: string | null;
  dataRecebimento?: string | null;
};

export type RestControleAjustes = RestOf<IControleAjustes>;

export type NewRestControleAjustes = RestOf<NewControleAjustes>;

export type PartialUpdateRestControleAjustes = RestOf<PartialUpdateControleAjustes>;

export type EntityResponseType = HttpResponse<IControleAjustes>;
export type EntityArrayResponseType = HttpResponse<IControleAjustes[]>;

@Injectable({ providedIn: 'root' })
export class ControleAjustesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/controle-ajustes');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(controleAjustes: NewControleAjustes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleAjustes);
    return this.http
      .post<RestControleAjustes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(controleAjustes: IControleAjustes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleAjustes);
    return this.http
      .put<RestControleAjustes>(`${this.resourceUrl}/${this.getControleAjustesIdentifier(controleAjustes)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(controleAjustes: PartialUpdateControleAjustes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleAjustes);
    return this.http
      .patch<RestControleAjustes>(`${this.resourceUrl}/${this.getControleAjustesIdentifier(controleAjustes)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestControleAjustes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestControleAjustes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getControleAjustesIdentifier(controleAjustes: Pick<IControleAjustes, 'id'>): number {
    return controleAjustes.id;
  }

  compareControleAjustes(o1: Pick<IControleAjustes, 'id'> | null, o2: Pick<IControleAjustes, 'id'> | null): boolean {
    return o1 && o2 ? this.getControleAjustesIdentifier(o1) === this.getControleAjustesIdentifier(o2) : o1 === o2;
  }

  addControleAjustesToCollectionIfMissing<Type extends Pick<IControleAjustes, 'id'>>(
    controleAjustesCollection: Type[],
    ...controleAjustesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const controleAjustes: Type[] = controleAjustesToCheck.filter(isPresent);
    if (controleAjustes.length > 0) {
      const controleAjustesCollectionIdentifiers = controleAjustesCollection.map(
        controleAjustesItem => this.getControleAjustesIdentifier(controleAjustesItem)!,
      );
      const controleAjustesToAdd = controleAjustes.filter(controleAjustesItem => {
        const controleAjustesIdentifier = this.getControleAjustesIdentifier(controleAjustesItem);
        if (controleAjustesCollectionIdentifiers.includes(controleAjustesIdentifier)) {
          return false;
        }
        controleAjustesCollectionIdentifiers.push(controleAjustesIdentifier);
        return true;
      });
      return [...controleAjustesToAdd, ...controleAjustesCollection];
    }
    return controleAjustesCollection;
  }

  protected convertDateFromClient<T extends IControleAjustes | NewControleAjustes | PartialUpdateControleAjustes>(
    controleAjustes: T,
  ): RestOf<T> {
    return {
      ...controleAjustes,
      dataEntrega: controleAjustes.dataEntrega?.format(DATE_FORMAT) ?? null,
      dataRecebimento: controleAjustes.dataRecebimento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restControleAjustes: RestControleAjustes): IControleAjustes {
    return {
      ...restControleAjustes,
      dataEntrega: restControleAjustes.dataEntrega ? dayjs(restControleAjustes.dataEntrega) : undefined,
      dataRecebimento: restControleAjustes.dataRecebimento ? dayjs(restControleAjustes.dataRecebimento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestControleAjustes>): HttpResponse<IControleAjustes> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestControleAjustes[]>): HttpResponse<IControleAjustes[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
