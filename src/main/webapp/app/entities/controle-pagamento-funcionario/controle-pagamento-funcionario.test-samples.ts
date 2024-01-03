import dayjs from 'dayjs/esm';

import { IControlePagamentoFuncionario, NewControlePagamentoFuncionario } from './controle-pagamento-funcionario.model';

export const sampleWithRequiredData: IControlePagamentoFuncionario = {
  id: 12208,
};

export const sampleWithPartialData: IControlePagamentoFuncionario = {
  id: 23558,
  beneficio: 1272.64,
};

export const sampleWithFullData: IControlePagamentoFuncionario = {
  id: 32031,
  data: dayjs('2024-01-02'),
  salario: 14942.95,
  beneficio: 7477.3,
  comissao: 13780.78,
  ferias: 22730.27,
  adiantamento: 11748.18,
  total: 14417.3,
};

export const sampleWithNewData: NewControlePagamentoFuncionario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
