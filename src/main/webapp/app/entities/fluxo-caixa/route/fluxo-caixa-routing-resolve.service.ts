import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFluxoCaixa } from '../fluxo-caixa.model';
import { FluxoCaixaService } from '../service/fluxo-caixa.service';

export const fluxoCaixaResolve = (route: ActivatedRouteSnapshot): Observable<null | IFluxoCaixa> => {
  const id = route.params['id'];
  if (id) {
    return inject(FluxoCaixaService)
      .find(id)
      .pipe(
        mergeMap((fluxoCaixa: HttpResponse<IFluxoCaixa>) => {
          if (fluxoCaixa.body) {
            return of(fluxoCaixa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default fluxoCaixaResolve;
