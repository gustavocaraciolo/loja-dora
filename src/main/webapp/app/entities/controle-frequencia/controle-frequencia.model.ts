import dayjs from 'dayjs/esm';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';

export interface IControleFrequencia {
  id: number;
  dataTrabalho?: dayjs.Dayjs | null;
  funcionarios?: IFuncionario[] | null;
}

export type NewControleFrequencia = Omit<IControleFrequencia, 'id'> & { id: null };
