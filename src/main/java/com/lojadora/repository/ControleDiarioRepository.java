package com.lojadora.repository;

import com.lojadora.domain.ControleDiario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControleDiario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleDiarioRepository extends JpaRepository<ControleDiario, Long> {}
