import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'fluxo-caixa',
    data: { pageTitle: 'lojaDoraApp.fluxoCaixa.home.title' },
    loadChildren: () => import('./fluxo-caixa/fluxo-caixa.routes'),
  },
  {
    path: 'entidades',
    data: { pageTitle: 'lojaDoraApp.entidades.home.title' },
    loadChildren: () => import('./entidades/entidades.routes'),
  },
  {
    path: 'controle-diario',
    data: { pageTitle: 'lojaDoraApp.controleDiario.home.title' },
    loadChildren: () => import('./controle-diario/controle-diario.routes'),
  },
  {
    path: 'controle-entregas',
    data: { pageTitle: 'lojaDoraApp.controleEntregas.home.title' },
    loadChildren: () => import('./controle-entregas/controle-entregas.routes'),
  },
  {
    path: 'controle-frequencia',
    data: { pageTitle: 'lojaDoraApp.controleFrequencia.home.title' },
    loadChildren: () => import('./controle-frequencia/controle-frequencia.routes'),
  },
  {
    path: 'controle-ajustes',
    data: { pageTitle: 'lojaDoraApp.controleAjustes.home.title' },
    loadChildren: () => import('./controle-ajustes/controle-ajustes.routes'),
  },
  {
    path: 'funcionario',
    data: { pageTitle: 'lojaDoraApp.funcionario.home.title' },
    loadChildren: () => import('./funcionario/funcionario.routes'),
  },
  {
    path: 'controle-pagamento-funcionario',
    data: { pageTitle: 'lojaDoraApp.controlePagamentoFuncionario.home.title' },
    loadChildren: () => import('./controle-pagamento-funcionario/controle-pagamento-funcionario.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
