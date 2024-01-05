package com.lojadora.repository;

import com.lojadora.domain.ControlePagamentoFuncionario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControlePagamentoFuncionario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlePagamentoFuncionarioRepository extends JpaRepository<ControlePagamentoFuncionario, Long> {}
