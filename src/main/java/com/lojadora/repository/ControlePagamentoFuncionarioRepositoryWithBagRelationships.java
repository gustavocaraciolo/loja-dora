package com.lojadora.repository;

import com.lojadora.domain.ControlePagamentoFuncionario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ControlePagamentoFuncionarioRepositoryWithBagRelationships {
    Optional<ControlePagamentoFuncionario> fetchBagRelationships(Optional<ControlePagamentoFuncionario> controlePagamentoFuncionario);

    List<ControlePagamentoFuncionario> fetchBagRelationships(List<ControlePagamentoFuncionario> controlePagamentoFuncionarios);

    Page<ControlePagamentoFuncionario> fetchBagRelationships(Page<ControlePagamentoFuncionario> controlePagamentoFuncionarios);
}
