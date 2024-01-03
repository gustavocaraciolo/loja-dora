import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ControleAjustesComponent } from './list/controle-ajustes.component';
import { ControleAjustesDetailComponent } from './detail/controle-ajustes-detail.component';
import { ControleAjustesUpdateComponent } from './update/controle-ajustes-update.component';
import ControleAjustesResolve from './route/controle-ajustes-routing-resolve.service';

const controleAjustesRoute: Routes = [
  {
    path: '',
    component: ControleAjustesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ControleAjustesDetailComponent,
    resolve: {
      controleAjustes: ControleAjustesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ControleAjustesUpdateComponent,
    resolve: {
      controleAjustes: ControleAjustesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ControleAjustesUpdateComponent,
    resolve: {
      controleAjustes: ControleAjustesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default controleAjustesRoute;
