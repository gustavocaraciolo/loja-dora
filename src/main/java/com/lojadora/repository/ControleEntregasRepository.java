package com.lojadora.repository;

import com.lojadora.domain.ControleEntregas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControleEntregas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleEntregasRepository extends JpaRepository<ControleEntregas, Long> {}
