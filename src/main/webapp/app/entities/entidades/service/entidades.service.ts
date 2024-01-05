import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntidades, NewEntidades } from '../entidades.model';

export type PartialUpdateEntidades = Partial<IEntidades> & Pick<IEntidades, 'id'>;

export type EntityResponseType = HttpResponse<IEntidades>;
export type EntityArrayResponseType = HttpResponse<IEntidades[]>;

@Injectable({ providedIn: 'root' })
export class EntidadesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entidades');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(entidades: NewEntidades): Observable<EntityResponseType> {
    return this.http.post<IEntidades>(this.resourceUrl, entidades, { observe: 'response' });
  }

  update(entidades: IEntidades): Observable<EntityResponseType> {
    return this.http.put<IEntidades>(`${this.resourceUrl}/${this.getEntidadesIdentifier(entidades)}`, entidades, { observe: 'response' });
  }

  partialUpdate(entidades: PartialUpdateEntidades): Observable<EntityResponseType> {
    return this.http.patch<IEntidades>(`${this.resourceUrl}/${this.getEntidadesIdentifier(entidades)}`, entidades, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntidades>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntidades[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEntidadesIdentifier(entidades: Pick<IEntidades, 'id'>): number {
    return entidades.id;
  }

  compareEntidades(o1: Pick<IEntidades, 'id'> | null, o2: Pick<IEntidades, 'id'> | null): boolean {
    return o1 && o2 ? this.getEntidadesIdentifier(o1) === this.getEntidadesIdentifier(o2) : o1 === o2;
  }

  addEntidadesToCollectionIfMissing<Type extends Pick<IEntidades, 'id'>>(
    entidadesCollection: Type[],
    ...entidadesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const entidades: Type[] = entidadesToCheck.filter(isPresent);
    if (entidades.length > 0) {
      const entidadesCollectionIdentifiers = entidadesCollection.map(entidadesItem => this.getEntidadesIdentifier(entidadesItem)!);
      const entidadesToAdd = entidades.filter(entidadesItem => {
        const entidadesIdentifier = this.getEntidadesIdentifier(entidadesItem);
        if (entidadesCollectionIdentifiers.includes(entidadesIdentifier)) {
          return false;
        }
        entidadesCollectionIdentifiers.push(entidadesIdentifier);
        return true;
      });
      return [...entidadesToAdd, ...entidadesCollection];
    }
    return entidadesCollection;
  }
}
