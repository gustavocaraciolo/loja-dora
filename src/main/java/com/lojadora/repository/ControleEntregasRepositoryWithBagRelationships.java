package com.lojadora.repository;

import com.lojadora.domain.ControleEntregas;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ControleEntregasRepositoryWithBagRelationships {
    Optional<ControleEntregas> fetchBagRelationships(Optional<ControleEntregas> controleEntregas);

    List<ControleEntregas> fetchBagRelationships(List<ControleEntregas> controleEntregases);

    Page<ControleEntregas> fetchBagRelationships(Page<ControleEntregas> controleEntregases);
}
