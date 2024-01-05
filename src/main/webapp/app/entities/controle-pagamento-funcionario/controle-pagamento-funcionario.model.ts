import dayjs from 'dayjs/esm';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';

export interface IControlePagamentoFuncionario {
  id: number;
  data?: dayjs.Dayjs | null;
  salario?: number | null;
  beneficio?: number | null;
  comissao?: number | null;
  ferias?: number | null;
  adiantamento?: number | null;
  total?: number | null;
  funcionario?: IFuncionario | null;
}

export type NewControlePagamentoFuncionario = Omit<IControlePagamentoFuncionario, 'id'> & { id: null };
