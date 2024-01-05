package com.lojadora.service;

import com.lojadora.domain.ControleEntregas;
import com.lojadora.repository.ControleEntregasRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.ControleEntregas}.
 */
@Service
@Transactional
public class ControleEntregasService {

    private final Logger log = LoggerFactory.getLogger(ControleEntregasService.class);

    private final ControleEntregasRepository controleEntregasRepository;

    public ControleEntregasService(ControleEntregasRepository controleEntregasRepository) {
        this.controleEntregasRepository = controleEntregasRepository;
    }

    /**
     * Save a controleEntregas.
     *
     * @param controleEntregas the entity to save.
     * @return the persisted entity.
     */
    public ControleEntregas save(ControleEntregas controleEntregas) {
        log.debug("Request to save ControleEntregas : {}", controleEntregas);
        return controleEntregasRepository.save(controleEntregas);
    }

    /**
     * Update a controleEntregas.
     *
     * @param controleEntregas the entity to save.
     * @return the persisted entity.
     */
    public ControleEntregas update(ControleEntregas controleEntregas) {
        log.debug("Request to update ControleEntregas : {}", controleEntregas);
        return controleEntregasRepository.save(controleEntregas);
    }

    /**
     * Partially update a controleEntregas.
     *
     * @param controleEntregas the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ControleEntregas> partialUpdate(ControleEntregas controleEntregas) {
        log.debug("Request to partially update ControleEntregas : {}", controleEntregas);

        return controleEntregasRepository
            .findById(controleEntregas.getId())
            .map(existingControleEntregas -> {
                if (controleEntregas.getData() != null) {
                    existingControleEntregas.setData(controleEntregas.getData());
                }
                if (controleEntregas.getDescricao() != null) {
                    existingControleEntregas.setDescricao(controleEntregas.getDescricao());
                }
                if (controleEntregas.getAddress() != null) {
                    existingControleEntregas.setAddress(controleEntregas.getAddress());
                }
                if (controleEntregas.getReceita() != null) {
                    existingControleEntregas.setReceita(controleEntregas.getReceita());
                }
                if (controleEntregas.getValor() != null) {
                    existingControleEntregas.setValor(controleEntregas.getValor());
                }

                return existingControleEntregas;
            })
            .map(controleEntregasRepository::save);
    }

    /**
     * Get all the controleEntregases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ControleEntregas> findAll(Pageable pageable) {
        log.debug("Request to get all ControleEntregases");
        return controleEntregasRepository.findAll(pageable);
    }

    /**
     * Get all the controleEntregases with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ControleEntregas> findAllWithEagerRelationships(Pageable pageable) {
        return controleEntregasRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one controleEntregas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ControleEntregas> findOne(Long id) {
        log.debug("Request to get ControleEntregas : {}", id);
        return controleEntregasRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the controleEntregas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ControleEntregas : {}", id);
        controleEntregasRepository.deleteById(id);
    }
}
