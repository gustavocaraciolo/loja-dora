import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ControlePagamentoFuncionarioComponent } from './list/controle-pagamento-funcionario.component';
import { ControlePagamentoFuncionarioDetailComponent } from './detail/controle-pagamento-funcionario-detail.component';
import { ControlePagamentoFuncionarioUpdateComponent } from './update/controle-pagamento-funcionario-update.component';
import ControlePagamentoFuncionarioResolve from './route/controle-pagamento-funcionario-routing-resolve.service';

const controlePagamentoFuncionarioRoute: Routes = [
  {
    path: '',
    component: ControlePagamentoFuncionarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ControlePagamentoFuncionarioDetailComponent,
    resolve: {
      controlePagamentoFuncionario: ControlePagamentoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ControlePagamentoFuncionarioUpdateComponent,
    resolve: {
      controlePagamentoFuncionario: ControlePagamentoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ControlePagamentoFuncionarioUpdateComponent,
    resolve: {
      controlePagamentoFuncionario: ControlePagamentoFuncionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default controlePagamentoFuncionarioRoute;
