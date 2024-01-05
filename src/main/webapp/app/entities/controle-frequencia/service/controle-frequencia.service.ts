import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IControleFrequencia, NewControleFrequencia } from '../controle-frequencia.model';

export type PartialUpdateControleFrequencia = Partial<IControleFrequencia> & Pick<IControleFrequencia, 'id'>;

type RestOf<T extends IControleFrequencia | NewControleFrequencia> = Omit<T, 'dataTrabalho'> & {
  dataTrabalho?: string | null;
};

export type RestControleFrequencia = RestOf<IControleFrequencia>;

export type NewRestControleFrequencia = RestOf<NewControleFrequencia>;

export type PartialUpdateRestControleFrequencia = RestOf<PartialUpdateControleFrequencia>;

export type EntityResponseType = HttpResponse<IControleFrequencia>;
export type EntityArrayResponseType = HttpResponse<IControleFrequencia[]>;

@Injectable({ providedIn: 'root' })
export class ControleFrequenciaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/controle-frequencias');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(controleFrequencia: NewControleFrequencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleFrequencia);
    return this.http
      .post<RestControleFrequencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(controleFrequencia: IControleFrequencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleFrequencia);
    return this.http
      .put<RestControleFrequencia>(`${this.resourceUrl}/${this.getControleFrequenciaIdentifier(controleFrequencia)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(controleFrequencia: PartialUpdateControleFrequencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleFrequencia);
    return this.http
      .patch<RestControleFrequencia>(`${this.resourceUrl}/${this.getControleFrequenciaIdentifier(controleFrequencia)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestControleFrequencia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestControleFrequencia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getControleFrequenciaIdentifier(controleFrequencia: Pick<IControleFrequencia, 'id'>): number {
    return controleFrequencia.id;
  }

  compareControleFrequencia(o1: Pick<IControleFrequencia, 'id'> | null, o2: Pick<IControleFrequencia, 'id'> | null): boolean {
    return o1 && o2 ? this.getControleFrequenciaIdentifier(o1) === this.getControleFrequenciaIdentifier(o2) : o1 === o2;
  }

  addControleFrequenciaToCollectionIfMissing<Type extends Pick<IControleFrequencia, 'id'>>(
    controleFrequenciaCollection: Type[],
    ...controleFrequenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const controleFrequencias: Type[] = controleFrequenciasToCheck.filter(isPresent);
    if (controleFrequencias.length > 0) {
      const controleFrequenciaCollectionIdentifiers = controleFrequenciaCollection.map(
        controleFrequenciaItem => this.getControleFrequenciaIdentifier(controleFrequenciaItem)!,
      );
      const controleFrequenciasToAdd = controleFrequencias.filter(controleFrequenciaItem => {
        const controleFrequenciaIdentifier = this.getControleFrequenciaIdentifier(controleFrequenciaItem);
        if (controleFrequenciaCollectionIdentifiers.includes(controleFrequenciaIdentifier)) {
          return false;
        }
        controleFrequenciaCollectionIdentifiers.push(controleFrequenciaIdentifier);
        return true;
      });
      return [...controleFrequenciasToAdd, ...controleFrequenciaCollection];
    }
    return controleFrequenciaCollection;
  }

  protected convertDateFromClient<T extends IControleFrequencia | NewControleFrequencia | PartialUpdateControleFrequencia>(
    controleFrequencia: T,
  ): RestOf<T> {
    return {
      ...controleFrequencia,
      dataTrabalho: controleFrequencia.dataTrabalho?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restControleFrequencia: RestControleFrequencia): IControleFrequencia {
    return {
      ...restControleFrequencia,
      dataTrabalho: restControleFrequencia.dataTrabalho ? dayjs(restControleFrequencia.dataTrabalho) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestControleFrequencia>): HttpResponse<IControleFrequencia> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestControleFrequencia[]>): HttpResponse<IControleFrequencia[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
