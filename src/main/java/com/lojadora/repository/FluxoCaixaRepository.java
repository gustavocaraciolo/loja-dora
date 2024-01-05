package com.lojadora.repository;

import com.lojadora.domain.FluxoCaixa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FluxoCaixa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FluxoCaixaRepository extends JpaRepository<FluxoCaixa, Long> {}
