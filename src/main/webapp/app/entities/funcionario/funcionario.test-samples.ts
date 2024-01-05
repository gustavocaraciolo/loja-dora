import dayjs from 'dayjs/esm';

import { IFuncionario, NewFuncionario } from './funcionario.model';

export const sampleWithRequiredData: IFuncionario = {
  id: 4802,
  primeiroNome: 'guacamole goggles',
  ultimoNome: 'purple verbally so',
};

export const sampleWithPartialData: IFuncionario = {
  id: 11952,
  primeiroNome: 'spiteful export disguised',
  ultimoNome: 'like',
  enderecoLinha1: 'tidy judgementally',
  telefoneEmergencial: 'calculating',
};

export const sampleWithFullData: IFuncionario = {
  id: 17958,
  primeiroNome: 'yum now greatly',
  ultimoNome: 'frayed',
  enderecoLinha1: 'yum occurrence',
  enderecoLinha2: 'stimulating regularly',
  dataInicio: dayjs('2024-01-03'),
  telefone: 'greedy ouch hefty',
  telefoneEmergencial: 'soft acidly until',
  email: 'Cecilia_Nogueira22@bol.com.br',
  banco: 'before externalize',
  iban: 'AZ29LQMI73002951009979708567',
};

export const sampleWithNewData: NewFuncionario = {
  primeiroNome: 'aha',
  ultimoNome: 'what um justly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
