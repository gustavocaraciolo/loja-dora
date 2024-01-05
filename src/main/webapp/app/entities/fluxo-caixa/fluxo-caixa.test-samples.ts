import dayjs from 'dayjs/esm';

import { IFluxoCaixa, NewFluxoCaixa } from './fluxo-caixa.model';

export const sampleWithRequiredData: IFluxoCaixa = {
  id: 15109,
};

export const sampleWithPartialData: IFluxoCaixa = {
  id: 25865,
  saldo: 7610.7,
  pais: 'ANGOLA',
};

export const sampleWithFullData: IFluxoCaixa = {
  id: 11178,
  data: dayjs('2024-01-03'),
  saldo: 19666.83,
  banco: 'NUBANK',
  valor: 17517.85,
  fixoVariavel: 'FIXO',
  entradaSaida: 'SAIDA',
  pais: 'BRASIL',
};

export const sampleWithNewData: NewFluxoCaixa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
