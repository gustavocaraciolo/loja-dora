package com.lojadora.repository;

import com.lojadora.domain.ControleAjustes;
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
public class ControleAjustesRepositoryWithBagRelationshipsImpl implements ControleAjustesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ControleAjustes> fetchBagRelationships(Optional<ControleAjustes> controleAjustes) {
        return controleAjustes.map(this::fetchFuncionarios);
    }

    @Override
    public Page<ControleAjustes> fetchBagRelationships(Page<ControleAjustes> controleAjustes) {
        return new PageImpl<>(
            fetchBagRelationships(controleAjustes.getContent()),
            controleAjustes.getPageable(),
            controleAjustes.getTotalElements()
        );
    }

    @Override
    public List<ControleAjustes> fetchBagRelationships(List<ControleAjustes> controleAjustes) {
        return Optional.of(controleAjustes).map(this::fetchFuncionarios).orElse(Collections.emptyList());
    }

    ControleAjustes fetchFuncionarios(ControleAjustes result) {
        return entityManager
            .createQuery(
                "select controleAjustes from ControleAjustes controleAjustes left join fetch controleAjustes.funcionarios where controleAjustes.id = :id",
                ControleAjustes.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<ControleAjustes> fetchFuncionarios(List<ControleAjustes> controleAjustes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, controleAjustes.size()).forEach(index -> order.put(controleAjustes.get(index).getId(), index));
        List<ControleAjustes> result = entityManager
            .createQuery(
                "select controleAjustes from ControleAjustes controleAjustes left join fetch controleAjustes.funcionarios where controleAjustes in :controleAjustes",
                ControleAjustes.class
            )
            .setParameter("controleAjustes", controleAjustes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
