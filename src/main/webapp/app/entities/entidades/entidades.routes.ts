import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EntidadesComponent } from './list/entidades.component';
import { EntidadesDetailComponent } from './detail/entidades-detail.component';
import { EntidadesUpdateComponent } from './update/entidades-update.component';
import EntidadesResolve from './route/entidades-routing-resolve.service';

const entidadesRoute: Routes = [
  {
    path: '',
    component: EntidadesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntidadesDetailComponent,
    resolve: {
      entidades: EntidadesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntidadesUpdateComponent,
    resolve: {
      entidades: EntidadesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntidadesUpdateComponent,
    resolve: {
      entidades: EntidadesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default entidadesRoute;
