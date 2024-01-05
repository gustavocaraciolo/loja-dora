import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ControleFrequenciaComponent } from './list/controle-frequencia.component';
import { ControleFrequenciaDetailComponent } from './detail/controle-frequencia-detail.component';
import { ControleFrequenciaUpdateComponent } from './update/controle-frequencia-update.component';
import ControleFrequenciaResolve from './route/controle-frequencia-routing-resolve.service';

const controleFrequenciaRoute: Routes = [
  {
    path: '',
    component: ControleFrequenciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ControleFrequenciaDetailComponent,
    resolve: {
      controleFrequencia: ControleFrequenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ControleFrequenciaUpdateComponent,
    resolve: {
      controleFrequencia: ControleFrequenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ControleFrequenciaUpdateComponent,
    resolve: {
      controleFrequencia: ControleFrequenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default controleFrequenciaRoute;
