import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IControleDiario } from '../controle-diario.model';
import { ControleDiarioService } from '../service/controle-diario.service';

export const controleDiarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IControleDiario> => {
  const id = route.params['id'];
  if (id) {
    return inject(ControleDiarioService)
      .find(id)
      .pipe(
        mergeMap((controleDiario: HttpResponse<IControleDiario>) => {
          if (controleDiario.body) {
            return of(controleDiario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default controleDiarioResolve;
