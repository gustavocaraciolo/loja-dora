package com.lojadora.repository;

import com.lojadora.domain.FluxoCaixa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FluxoCaixaRepositoryWithBagRelationships {
    Optional<FluxoCaixa> fetchBagRelationships(Optional<FluxoCaixa> fluxoCaixa);

    List<FluxoCaixa> fetchBagRelationships(List<FluxoCaixa> fluxoCaixas);

    Page<FluxoCaixa> fetchBagRelationships(Page<FluxoCaixa> fluxoCaixas);
}
