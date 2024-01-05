import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IControlePagamentoFuncionario } from '../controle-pagamento-funcionario.model';
import { ControlePagamentoFuncionarioService } from '../service/controle-pagamento-funcionario.service';

export const controlePagamentoFuncionarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IControlePagamentoFuncionario> => {
  const id = route.params['id'];
  if (id) {
    return inject(ControlePagamentoFuncionarioService)
      .find(id)
      .pipe(
        mergeMap((controlePagamentoFuncionario: HttpResponse<IControlePagamentoFuncionario>) => {
          if (controlePagamentoFuncionario.body) {
            return of(controlePagamentoFuncionario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default controlePagamentoFuncionarioResolve;
