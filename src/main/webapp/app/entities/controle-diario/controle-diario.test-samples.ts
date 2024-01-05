import dayjs from 'dayjs/esm';

import { IControleDiario, NewControleDiario } from './controle-diario.model';

export const sampleWithRequiredData: IControleDiario = {
  id: 15718,
};

export const sampleWithPartialData: IControleDiario = {
  id: 15499,
  recebimento: 22329.06,
  pagamento: 'DINHEIRO',
};

export const sampleWithFullData: IControleDiario = {
  id: 20659,
  data: dayjs('2024-01-03'),
  cliente: 'subedit bah blond',
  valorCompra: 30914.36,
  valorPago: 27001.88,
  saldoDevedor: 23716.41,
  recebimento: 14715.2,
  pagamento: 'DINHEIRO',
  banco: 'NUBANK',
};

export const sampleWithNewData: NewControleDiario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
