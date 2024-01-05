package com.lojadora.repository;

import com.lojadora.domain.ControleAjustes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ControleAjustesRepositoryWithBagRelationships {
    Optional<ControleAjustes> fetchBagRelationships(Optional<ControleAjustes> controleAjustes);

    List<ControleAjustes> fetchBagRelationships(List<ControleAjustes> controleAjustes);

    Page<ControleAjustes> fetchBagRelationships(Page<ControleAjustes> controleAjustes);
}
