package com.lojadora.repository;

import com.lojadora.domain.ControleEntregas;
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
public class ControleEntregasRepositoryWithBagRelationshipsImpl implements ControleEntregasRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ControleEntregas> fetchBagRelationships(Optional<ControleEntregas> controleEntregas) {
        return controleEntregas.map(this::fetchFuncionarios);
    }

    @Override
    public Page<ControleEntregas> fetchBagRelationships(Page<ControleEntregas> controleEntregases) {
        return new PageImpl<>(
            fetchBagRelationships(controleEntregases.getContent()),
            controleEntregases.getPageable(),
            controleEntregases.getTotalElements()
        );
    }

    @Override
    public List<ControleEntregas> fetchBagRelationships(List<ControleEntregas> controleEntregases) {
        return Optional.of(controleEntregases).map(this::fetchFuncionarios).orElse(Collections.emptyList());
    }

    ControleEntregas fetchFuncionarios(ControleEntregas result) {
        return entityManager
            .createQuery(
                "select controleEntregas from ControleEntregas controleEntregas left join fetch controleEntregas.funcionarios where controleEntregas.id = :id",
                ControleEntregas.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<ControleEntregas> fetchFuncionarios(List<ControleEntregas> controleEntregases) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, controleEntregases.size()).forEach(index -> order.put(controleEntregases.get(index).getId(), index));
        List<ControleEntregas> result = entityManager
            .createQuery(
                "select controleEntregas from ControleEntregas controleEntregas left join fetch controleEntregas.funcionarios where controleEntregas in :controleEntregases",
                ControleEntregas.class
            )
            .setParameter("controleEntregases", controleEntregases)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
