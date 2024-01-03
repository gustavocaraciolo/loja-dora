package com.lojadora.repository;

import com.lojadora.domain.FluxoCaixa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FluxoCaixa entity.
 *
 * When extending this class, extend FluxoCaixaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface FluxoCaixaRepository extends FluxoCaixaRepositoryWithBagRelationships, JpaRepository<FluxoCaixa, Long> {
    default Optional<FluxoCaixa> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<FluxoCaixa> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<FluxoCaixa> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
