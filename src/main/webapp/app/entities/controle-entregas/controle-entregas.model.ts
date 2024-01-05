import dayjs from 'dayjs/esm';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { Receita } from 'app/entities/enumerations/receita.model';

export interface IControleEntregas {
  id: number;
  data?: dayjs.Dayjs | null;
  descricao?: string | null;
  endereco?: string | null;
  receita?: keyof typeof Receita | null;
  valor?: number | null;
  funcionario?: IFuncionario | null;
}

export type NewControleEntregas = Omit<IControleEntregas, 'id'> & { id: null };
