import dayjs from 'dayjs/esm';
import { FormasPagamento } from 'app/entities/enumerations/formas-pagamento.model';
import { Banco } from 'app/entities/enumerations/banco.model';

export interface IControleDiario {
  id: number;
  data?: dayjs.Dayjs | null;
  cliente?: string | null;
  valorCompra?: number | null;
  valorPago?: number | null;
  saldoDevedor?: number | null;
  recebimento?: number | null;
  pagamento?: keyof typeof FormasPagamento | null;
  banco?: keyof typeof Banco | null;
}

export type NewControleDiario = Omit<IControleDiario, 'id'> & { id: null };
