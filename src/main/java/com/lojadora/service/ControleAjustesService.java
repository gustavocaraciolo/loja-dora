package com.lojadora.service;

import com.lojadora.domain.ControleAjustes;
import com.lojadora.repository.ControleAjustesRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.ControleAjustes}.
 */
@Service
@Transactional
public class ControleAjustesService {

    private final Logger log = LoggerFactory.getLogger(ControleAjustesService.class);

    private final ControleAjustesRepository controleAjustesRepository;

    public ControleAjustesService(ControleAjustesRepository controleAjustesRepository) {
        this.controleAjustesRepository = controleAjustesRepository;
    }

    /**
     * Save a controleAjustes.
     *
     * @param controleAjustes the entity to save.
     * @return the persisted entity.
     */
    public ControleAjustes save(ControleAjustes controleAjustes) {
        log.debug("Request to save ControleAjustes : {}", controleAjustes);
        return controleAjustesRepository.save(controleAjustes);
    }

    /**
     * Update a controleAjustes.
     *
     * @param controleAjustes the entity to save.
     * @return the persisted entity.
     */
    public ControleAjustes update(ControleAjustes controleAjustes) {
        log.debug("Request to update ControleAjustes : {}", controleAjustes);
        return controleAjustesRepository.save(controleAjustes);
    }

    /**
     * Partially update a controleAjustes.
     *
     * @param controleAjustes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ControleAjustes> partialUpdate(ControleAjustes controleAjustes) {
        log.debug("Request to partially update ControleAjustes : {}", controleAjustes);

        return controleAjustesRepository
            .findById(controleAjustes.getId())
            .map(existingControleAjustes -> {
                if (controleAjustes.getDataEntrega() != null) {
                    existingControleAjustes.setDataEntrega(controleAjustes.getDataEntrega());
                }
                if (controleAjustes.getDataRecebimento() != null) {
                    existingControleAjustes.setDataRecebimento(controleAjustes.getDataRecebimento());
                }
                if (controleAjustes.getQtdPecas() != null) {
                    existingControleAjustes.setQtdPecas(controleAjustes.getQtdPecas());
                }
                if (controleAjustes.getDescricao() != null) {
                    existingControleAjustes.setDescricao(controleAjustes.getDescricao());
                }
                if (controleAjustes.getValor() != null) {
                    existingControleAjustes.setValor(controleAjustes.getValor());
                }
                if (controleAjustes.getReceita() != null) {
                    existingControleAjustes.setReceita(controleAjustes.getReceita());
                }

                return existingControleAjustes;
            })
            .map(controleAjustesRepository::save);
    }

    /**
     * Get all the controleAjustes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ControleAjustes> findAll() {
        log.debug("Request to get all ControleAjustes");
        return controleAjustesRepository.findAll();
    }

    /**
     * Get all the controleAjustes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ControleAjustes> findAllWithEagerRelationships(Pageable pageable) {
        return controleAjustesRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one controleAjustes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ControleAjustes> findOne(Long id) {
        log.debug("Request to get ControleAjustes : {}", id);
        return controleAjustesRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the controleAjustes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ControleAjustes : {}", id);
        controleAjustesRepository.deleteById(id);
    }
}
