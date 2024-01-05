import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IControleEntregas } from '../controle-entregas.model';
import { ControleEntregasService } from '../service/controle-entregas.service';

export const controleEntregasResolve = (route: ActivatedRouteSnapshot): Observable<null | IControleEntregas> => {
  const id = route.params['id'];
  if (id) {
    return inject(ControleEntregasService)
      .find(id)
      .pipe(
        mergeMap((controleEntregas: HttpResponse<IControleEntregas>) => {
          if (controleEntregas.body) {
            return of(controleEntregas.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default controleEntregasResolve;
