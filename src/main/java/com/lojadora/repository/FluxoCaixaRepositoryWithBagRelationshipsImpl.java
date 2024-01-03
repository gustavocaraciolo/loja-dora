package com.lojadora.repository;

import com.lojadora.domain.FluxoCaixa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FluxoCaixaRepositoryWithBagRelationshipsImpl implements FluxoCaixaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<FluxoCaixa> fetchBagRelationships(Optional<FluxoCaixa> fluxoCaixa) {
        return fluxoCaixa.map(this::fetchEntidades);
    }

    @Override
    public Page<FluxoCaixa> fetchBagRelationships(Page<FluxoCaixa> fluxoCaixas) {
        return new PageImpl<>(fetchBagRelationships(fluxoCaixas.getContent()), fluxoCaixas.getPageable(), fluxoCaixas.getTotalElements());
    }

    @Override
    public List<FluxoCaixa> fetchBagRelationships(List<FluxoCaixa> fluxoCaixas) {
        return Optional.of(fluxoCaixas).map(this::fetchEntidades).orElse(Collections.emptyList());
    }

    FluxoCaixa fetchEntidades(FluxoCaixa result) {
        return entityManager
            .createQuery(
                "select fluxoCaixa from FluxoCaixa fluxoCaixa left join fetch fluxoCaixa.entidades where fluxoCaixa.id = :id",
                FluxoCaixa.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<FluxoCaixa> fetchEntidades(List<FluxoCaixa> fluxoCaixas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fluxoCaixas.size()).forEach(index -> order.put(fluxoCaixas.get(index).getId(), index));
        List<FluxoCaixa> result = entityManager
            .createQuery(
                "select fluxoCaixa from FluxoCaixa fluxoCaixa left join fetch fluxoCaixa.entidades where fluxoCaixa in :fluxoCaixas",
                FluxoCaixa.class
            )
            .setParameter("fluxoCaixas", fluxoCaixas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
