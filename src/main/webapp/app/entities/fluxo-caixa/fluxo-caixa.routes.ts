import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FluxoCaixaComponent } from './list/fluxo-caixa.component';
import { FluxoCaixaDetailComponent } from './detail/fluxo-caixa-detail.component';
import { FluxoCaixaUpdateComponent } from './update/fluxo-caixa-update.component';
import FluxoCaixaResolve from './route/fluxo-caixa-routing-resolve.service';

const fluxoCaixaRoute: Routes = [
  {
    path: '',
    component: FluxoCaixaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FluxoCaixaDetailComponent,
    resolve: {
      fluxoCaixa: FluxoCaixaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FluxoCaixaUpdateComponent,
    resolve: {
      fluxoCaixa: FluxoCaixaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FluxoCaixaUpdateComponent,
    resolve: {
      fluxoCaixa: FluxoCaixaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fluxoCaixaRoute;
