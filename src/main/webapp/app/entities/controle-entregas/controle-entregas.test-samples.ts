import dayjs from 'dayjs/esm';

import { IControleEntregas, NewControleEntregas } from './controle-entregas.model';

export const sampleWithRequiredData: IControleEntregas = {
  id: 18560,
};

export const sampleWithPartialData: IControleEntregas = {
  id: 25012,
  data: dayjs('2024-01-02'),
  receita: 'DORA',
};

export const sampleWithFullData: IControleEntregas = {
  id: 25937,
  data: dayjs('2024-01-03'),
  descricao: 'moonlight stack involvement',
  endereco: 'sans vigorous menopause',
  receita: 'CLIENTE',
  valor: 31147.79,
};

export const sampleWithNewData: NewControleEntregas = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
