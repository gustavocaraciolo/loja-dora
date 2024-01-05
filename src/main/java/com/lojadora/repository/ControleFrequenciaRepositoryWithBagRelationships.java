package com.lojadora.repository;

import com.lojadora.domain.ControleFrequencia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ControleFrequenciaRepositoryWithBagRelationships {
    Optional<ControleFrequencia> fetchBagRelationships(Optional<ControleFrequencia> controleFrequencia);

    List<ControleFrequencia> fetchBagRelationships(List<ControleFrequencia> controleFrequencias);

    Page<ControleFrequencia> fetchBagRelationships(Page<ControleFrequencia> controleFrequencias);
}
