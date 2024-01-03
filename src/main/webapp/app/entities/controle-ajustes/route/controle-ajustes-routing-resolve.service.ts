import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IControleAjustes } from '../controle-ajustes.model';
import { ControleAjustesService } from '../service/controle-ajustes.service';

export const controleAjustesResolve = (route: ActivatedRouteSnapshot): Observable<null | IControleAjustes> => {
  const id = route.params['id'];
  if (id) {
    return inject(ControleAjustesService)
      .find(id)
      .pipe(
        mergeMap((controleAjustes: HttpResponse<IControleAjustes>) => {
          if (controleAjustes.body) {
            return of(controleAjustes.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default controleAjustesResolve;
