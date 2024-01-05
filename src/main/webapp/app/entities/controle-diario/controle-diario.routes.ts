import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ControleDiarioComponent } from './list/controle-diario.component';
import { ControleDiarioDetailComponent } from './detail/controle-diario-detail.component';
import { ControleDiarioUpdateComponent } from './update/controle-diario-update.component';
import ControleDiarioResolve from './route/controle-diario-routing-resolve.service';

const controleDiarioRoute: Routes = [
  {
    path: '',
    component: ControleDiarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ControleDiarioDetailComponent,
    resolve: {
      controleDiario: ControleDiarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ControleDiarioUpdateComponent,
    resolve: {
      controleDiario: ControleDiarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ControleDiarioUpdateComponent,
    resolve: {
      controleDiario: ControleDiarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default controleDiarioRoute;
