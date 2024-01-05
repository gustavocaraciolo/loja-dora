import dayjs from 'dayjs/esm';

import { IControleFrequencia, NewControleFrequencia } from './controle-frequencia.model';

export const sampleWithRequiredData: IControleFrequencia = {
  id: 23732,
};

export const sampleWithPartialData: IControleFrequencia = {
  id: 7838,
};

export const sampleWithFullData: IControleFrequencia = {
  id: 14164,
  dataTrabalho: dayjs('2024-01-03'),
};

export const sampleWithNewData: NewControleFrequencia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
