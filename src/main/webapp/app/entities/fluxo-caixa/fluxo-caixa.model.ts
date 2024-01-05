import dayjs from 'dayjs/esm';
import { IEntidades } from 'app/entities/entidades/entidades.model';
import { Banco } from 'app/entities/enumerations/banco.model';
import { FixoVariavel } from 'app/entities/enumerations/fixo-variavel.model';
import { EntradaSaida } from 'app/entities/enumerations/entrada-saida.model';
import { Pais } from 'app/entities/enumerations/pais.model';

export interface IFluxoCaixa {
  id: number;
  data?: dayjs.Dayjs | null;
  saldo?: number | null;
  banco?: keyof typeof Banco | null;
  valor?: number | null;
  fixoVariavel?: keyof typeof FixoVariavel | null;
  entradaSaida?: keyof typeof EntradaSaida | null;
  pais?: keyof typeof Pais | null;
  entidades?: IEntidades | null;
}

export type NewFluxoCaixa = Omit<IFluxoCaixa, 'id'> & { id: null };
