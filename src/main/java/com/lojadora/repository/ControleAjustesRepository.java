package com.lojadora.repository;

import com.lojadora.domain.ControleAjustes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControleAjustes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleAjustesRepository extends JpaRepository<ControleAjustes, Long> {}
