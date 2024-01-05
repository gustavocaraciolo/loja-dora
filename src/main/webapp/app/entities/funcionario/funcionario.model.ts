import dayjs from 'dayjs/esm';
import { IControleEntregas } from 'app/entities/controle-entregas/controle-entregas.model';
import { IControlePagamentoFuncionario } from 'app/entities/controle-pagamento-funcionario/controle-pagamento-funcionario.model';
import { IControleAjustes } from 'app/entities/controle-ajustes/controle-ajustes.model';
import { IControleFrequencia } from 'app/entities/controle-frequencia/controle-frequencia.model';

export interface IFuncionario {
  id: number;
  primeiroNome?: string | null;
  ultimoNome?: string | null;
  enderecoLinha1?: string | null;
  enderecoLinha2?: string | null;
  dataInicio?: dayjs.Dayjs | null;
  telefone?: string | null;
  telefoneEmergencial?: string | null;
  email?: string | null;
  banco?: string | null;
  iban?: string | null;
  controleEntregases?: IControleEntregas[] | null;
  controlePagamentoFuncionarios?: IControlePagamentoFuncionario[] | null;
  controleAjustes?: IControleAjustes[] | null;
  controleFrequencias?: IControleFrequencia[] | null;
}

export type NewFuncionario = Omit<IFuncionario, 'id'> & { id: null };
