import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ControleEntregasComponent } from './list/controle-entregas.component';
import { ControleEntregasDetailComponent } from './detail/controle-entregas-detail.component';
import { ControleEntregasUpdateComponent } from './update/controle-entregas-update.component';
import ControleEntregasResolve from './route/controle-entregas-routing-resolve.service';

const controleEntregasRoute: Routes = [
  {
    path: '',
    component: ControleEntregasComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ControleEntregasDetailComponent,
    resolve: {
      controleEntregas: ControleEntregasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ControleEntregasUpdateComponent,
    resolve: {
      controleEntregas: ControleEntregasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ControleEntregasUpdateComponent,
    resolve: {
      controleEntregas: ControleEntregasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default controleEntregasRoute;
