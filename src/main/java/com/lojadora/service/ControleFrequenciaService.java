package com.lojadora.service;

import com.lojadora.domain.ControleFrequencia;
import com.lojadora.repository.ControleFrequenciaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.ControleFrequencia}.
 */
@Service
@Transactional
public class ControleFrequenciaService {

    private final Logger log = LoggerFactory.getLogger(ControleFrequenciaService.class);

    private final ControleFrequenciaRepository controleFrequenciaRepository;

    public ControleFrequenciaService(ControleFrequenciaRepository controleFrequenciaRepository) {
        this.controleFrequenciaRepository = controleFrequenciaRepository;
    }

    /**
     * Save a controleFrequencia.
     *
     * @param controleFrequencia the entity to save.
     * @return the persisted entity.
     */
    public ControleFrequencia save(ControleFrequencia controleFrequencia) {
        log.debug("Request to save ControleFrequencia : {}", controleFrequencia);
        return controleFrequenciaRepository.save(controleFrequencia);
    }

    /**
     * Update a controleFrequencia.
     *
     * @param controleFrequencia the entity to save.
     * @return the persisted entity.
     */
    public ControleFrequencia update(ControleFrequencia controleFrequencia) {
        log.debug("Request to update ControleFrequencia : {}", controleFrequencia);
        return controleFrequenciaRepository.save(controleFrequencia);
    }

    /**
     * Partially update a controleFrequencia.
     *
     * @param controleFrequencia the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ControleFrequencia> partialUpdate(ControleFrequencia controleFrequencia) {
        log.debug("Request to partially update ControleFrequencia : {}", controleFrequencia);

        return controleFrequenciaRepository
            .findById(controleFrequencia.getId())
            .map(existingControleFrequencia -> {
                if (controleFrequencia.getDataTrabalho() != null) {
                    existingControleFrequencia.setDataTrabalho(controleFrequencia.getDataTrabalho());
                }

                return existingControleFrequencia;
            })
            .map(controleFrequenciaRepository::save);
    }

    /**
     * Get all the controleFrequencias.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ControleFrequencia> findAll() {
        log.debug("Request to get all ControleFrequencias");
        return controleFrequenciaRepository.findAll();
    }

    /**
     * Get all the controleFrequencias with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ControleFrequencia> findAllWithEagerRelationships(Pageable pageable) {
        return controleFrequenciaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one controleFrequencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ControleFrequencia> findOne(Long id) {
        log.debug("Request to get ControleFrequencia : {}", id);
        return controleFrequenciaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the controleFrequencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ControleFrequencia : {}", id);
        controleFrequenciaRepository.deleteById(id);
    }
}
