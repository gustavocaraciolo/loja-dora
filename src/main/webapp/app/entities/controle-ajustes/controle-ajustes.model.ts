import dayjs from 'dayjs/esm';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { Receita } from 'app/entities/enumerations/receita.model';

export interface IControleAjustes {
  id: number;
  dataEntrega?: dayjs.Dayjs | null;
  dataRecebimento?: dayjs.Dayjs | null;
  qtdPecas?: number | null;
  descricao?: string | null;
  valor?: number | null;
  receita?: keyof typeof Receita | null;
  funcionario?: IFuncionario | null;
}

export type NewControleAjustes = Omit<IControleAjustes, 'id'> & { id: null };
