package com.lojadora.repository;

import com.lojadora.domain.ControleFrequencia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControleFrequencia entity.
 *
 * When extending this class, extend ControleFrequenciaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ControleFrequenciaRepository
    extends ControleFrequenciaRepositoryWithBagRelationships, JpaRepository<ControleFrequencia, Long> {
    default Optional<ControleFrequencia> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ControleFrequencia> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ControleFrequencia> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
