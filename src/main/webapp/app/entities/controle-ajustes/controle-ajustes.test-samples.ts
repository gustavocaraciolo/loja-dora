import dayjs from 'dayjs/esm';

import { IControleAjustes, NewControleAjustes } from './controle-ajustes.model';

export const sampleWithRequiredData: IControleAjustes = {
  id: 21714,
};

export const sampleWithPartialData: IControleAjustes = {
  id: 21592,
  dataEntrega: dayjs('2024-01-02'),
  valor: 22186.27,
};

export const sampleWithFullData: IControleAjustes = {
  id: 29111,
  dataEntrega: dayjs('2024-01-03'),
  dataRecebimento: dayjs('2024-01-02'),
  qtdPecas: 25505,
  descricao: 'whoa mute obediently',
  valor: 16611.15,
  receita: 'DORA',
};

export const sampleWithNewData: NewControleAjustes = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
