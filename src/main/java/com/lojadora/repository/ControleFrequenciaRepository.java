package com.lojadora.repository;

import com.lojadora.domain.ControleFrequencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControleFrequencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleFrequenciaRepository extends JpaRepository<ControleFrequencia, Long> {}
