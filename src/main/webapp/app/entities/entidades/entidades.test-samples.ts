import { IEntidades, NewEntidades } from './entidades.model';

export const sampleWithRequiredData: IEntidades = {
  id: 27931,
};

export const sampleWithPartialData: IEntidades = {
  id: 22100,
  nome: 'since via combat',
};

export const sampleWithFullData: IEntidades = {
  id: 9444,
  nome: 'softly inside blah',
  endereco: 'oh',
  telefone: 28248,
};

export const sampleWithNewData: NewEntidades = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
