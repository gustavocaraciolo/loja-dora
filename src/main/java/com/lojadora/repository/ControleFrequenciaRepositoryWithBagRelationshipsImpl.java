package com.lojadora.repository;

import com.lojadora.domain.ControleFrequencia;
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
public class ControleFrequenciaRepositoryWithBagRelationshipsImpl implements ControleFrequenciaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ControleFrequencia> fetchBagRelationships(Optional<ControleFrequencia> controleFrequencia) {
        return controleFrequencia.map(this::fetchFuncionarios);
    }

    @Override
    public Page<ControleFrequencia> fetchBagRelationships(Page<ControleFrequencia> controleFrequencias) {
        return new PageImpl<>(
            fetchBagRelationships(controleFrequencias.getContent()),
            controleFrequencias.getPageable(),
            controleFrequencias.getTotalElements()
        );
    }

    @Override
    public List<ControleFrequencia> fetchBagRelationships(List<ControleFrequencia> controleFrequencias) {
        return Optional.of(controleFrequencias).map(this::fetchFuncionarios).orElse(Collections.emptyList());
    }

    ControleFrequencia fetchFuncionarios(ControleFrequencia result) {
        return entityManager
            .createQuery(
                "select controleFrequencia from ControleFrequencia controleFrequencia left join fetch controleFrequencia.funcionarios where controleFrequencia.id = :id",
                ControleFrequencia.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<ControleFrequencia> fetchFuncionarios(List<ControleFrequencia> controleFrequencias) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, controleFrequencias.size()).forEach(index -> order.put(controleFrequencias.get(index).getId(), index));
        List<ControleFrequencia> result = entityManager
            .createQuery(
                "select controleFrequencia from ControleFrequencia controleFrequencia left join fetch controleFrequencia.funcionarios where controleFrequencia in :controleFrequencias",
                ControleFrequencia.class
            )
            .setParameter("controleFrequencias", controleFrequencias)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
