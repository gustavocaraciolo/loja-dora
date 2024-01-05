package com.lojadora.service;

import com.lojadora.domain.Entidades;
import com.lojadora.repository.EntidadesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.Entidades}.
 */
@Service
@Transactional
public class EntidadesService {

    private final Logger log = LoggerFactory.getLogger(EntidadesService.class);

    private final EntidadesRepository entidadesRepository;

    public EntidadesService(EntidadesRepository entidadesRepository) {
        this.entidadesRepository = entidadesRepository;
    }

    /**
     * Save a entidades.
     *
     * @param entidades the entity to save.
     * @return the persisted entity.
     */
    public Entidades save(Entidades entidades) {
        log.debug("Request to save Entidades : {}", entidades);
        return entidadesRepository.save(entidades);
    }

    /**
     * Update a entidades.
     *
     * @param entidades the entity to save.
     * @return the persisted entity.
     */
    public Entidades update(Entidades entidades) {
        log.debug("Request to update Entidades : {}", entidades);
        return entidadesRepository.save(entidades);
    }

    /**
     * Partially update a entidades.
     *
     * @param entidades the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Entidades> partialUpdate(Entidades entidades) {
        log.debug("Request to partially update Entidades : {}", entidades);

        return entidadesRepository
            .findById(entidades.getId())
            .map(existingEntidades -> {
                if (entidades.getNome() != null) {
                    existingEntidades.setNome(entidades.getNome());
                }
                if (entidades.getEndereco() != null) {
                    existingEntidades.setEndereco(entidades.getEndereco());
                }
                if (entidades.getTelefone() != null) {
                    existingEntidades.setTelefone(entidades.getTelefone());
                }

                return existingEntidades;
            })
            .map(entidadesRepository::save);
    }

    /**
     * Get all the entidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Entidades> findAll(Pageable pageable) {
        log.debug("Request to get all Entidades");
        return entidadesRepository.findAll(pageable);
    }

    /**
     * Get one entidades by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Entidades> findOne(Long id) {
        log.debug("Request to get Entidades : {}", id);
        return entidadesRepository.findById(id);
    }

    /**
     * Delete the entidades by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entidades : {}", id);
        entidadesRepository.deleteById(id);
    }
}
