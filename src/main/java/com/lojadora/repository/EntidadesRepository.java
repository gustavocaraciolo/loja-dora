package com.lojadora.repository;

import com.lojadora.domain.Entidades;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Entidades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadesRepository extends JpaRepository<Entidades, Long> {}
