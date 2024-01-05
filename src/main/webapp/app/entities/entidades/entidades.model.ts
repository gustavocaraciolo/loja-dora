import { IFluxoCaixa } from 'app/entities/fluxo-caixa/fluxo-caixa.model';

export interface IEntidades {
  id: number;
  nome?: string | null;
  endereco?: string | null;
  telefone?: number | null;
  fluxoCaixas?: IFluxoCaixa[] | null;
}

export type NewEntidades = Omit<IEntidades, 'id'> & { id: null };
