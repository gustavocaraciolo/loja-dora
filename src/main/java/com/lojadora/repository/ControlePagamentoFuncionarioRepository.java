package com.lojadora.repository;

import com.lojadora.domain.ControlePagamentoFuncionario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControlePagamentoFuncionario entity.
 *
 * When extending this class, extend ControlePagamentoFuncionarioRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ControlePagamentoFuncionarioRepository
    extends ControlePagamentoFuncionarioRepositoryWithBagRelationships, JpaRepository<ControlePagamentoFuncionario, Long> {
    default Optional<ControlePagamentoFuncionario> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ControlePagamentoFuncionario> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ControlePagamentoFuncionario> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
