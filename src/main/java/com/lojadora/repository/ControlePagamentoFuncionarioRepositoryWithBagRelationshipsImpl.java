package com.lojadora.repository;

import com.lojadora.domain.ControlePagamentoFuncionario;
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
public class ControlePagamentoFuncionarioRepositoryWithBagRelationshipsImpl
    implements ControlePagamentoFuncionarioRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ControlePagamentoFuncionario> fetchBagRelationships(
        Optional<ControlePagamentoFuncionario> controlePagamentoFuncionario
    ) {
        return controlePagamentoFuncionario.map(this::fetchFuncionarios);
    }

    @Override
    public Page<ControlePagamentoFuncionario> fetchBagRelationships(Page<ControlePagamentoFuncionario> controlePagamentoFuncionarios) {
        return new PageImpl<>(
            fetchBagRelationships(controlePagamentoFuncionarios.getContent()),
            controlePagamentoFuncionarios.getPageable(),
            controlePagamentoFuncionarios.getTotalElements()
        );
    }

    @Override
    public List<ControlePagamentoFuncionario> fetchBagRelationships(List<ControlePagamentoFuncionario> controlePagamentoFuncionarios) {
        return Optional.of(controlePagamentoFuncionarios).map(this::fetchFuncionarios).orElse(Collections.emptyList());
    }

    ControlePagamentoFuncionario fetchFuncionarios(ControlePagamentoFuncionario result) {
        return entityManager
            .createQuery(
                "select controlePagamentoFuncionario from ControlePagamentoFuncionario controlePagamentoFuncionario left join fetch controlePagamentoFuncionario.funcionarios where controlePagamentoFuncionario.id = :id",
                ControlePagamentoFuncionario.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<ControlePagamentoFuncionario> fetchFuncionarios(List<ControlePagamentoFuncionario> controlePagamentoFuncionarios) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream
            .range(0, controlePagamentoFuncionarios.size())
            .forEach(index -> order.put(controlePagamentoFuncionarios.get(index).getId(), index));
        List<ControlePagamentoFuncionario> result = entityManager
            .createQuery(
                "select controlePagamentoFuncionario from ControlePagamentoFuncionario controlePagamentoFuncionario left join fetch controlePagamentoFuncionario.funcionarios where controlePagamentoFuncionario in :controlePagamentoFuncionarios",
                ControlePagamentoFuncionario.class
            )
            .setParameter("controlePagamentoFuncionarios", controlePagamentoFuncionarios)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
