import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IControleFrequencia } from '../controle-frequencia.model';
import { ControleFrequenciaService } from '../service/controle-frequencia.service';

export const controleFrequenciaResolve = (route: ActivatedRouteSnapshot): Observable<null | IControleFrequencia> => {
  const id = route.params['id'];
  if (id) {
    return inject(ControleFrequenciaService)
      .find(id)
      .pipe(
        mergeMap((controleFrequencia: HttpResponse<IControleFrequencia>) => {
          if (controleFrequencia.body) {
            return of(controleFrequencia.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default controleFrequenciaResolve;
