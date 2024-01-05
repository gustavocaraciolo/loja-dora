package com.lojadora.service;

import com.lojadora.domain.ControleDiario;
import com.lojadora.repository.ControleDiarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.ControleDiario}.
 */
@Service
@Transactional
public class ControleDiarioService {

    private final Logger log = LoggerFactory.getLogger(ControleDiarioService.class);

    private final ControleDiarioRepository controleDiarioRepository;

    public ControleDiarioService(ControleDiarioRepository controleDiarioRepository) {
        this.controleDiarioRepository = controleDiarioRepository;
    }

    /**
     * Save a controleDiario.
     *
     * @param controleDiario the entity to save.
     * @return the persisted entity.
     */
    public ControleDiario save(ControleDiario controleDiario) {
        log.debug("Request to save ControleDiario : {}", controleDiario);
        return controleDiarioRepository.save(controleDiario);
    }

    /**
     * Update a controleDiario.
     *
     * @param controleDiario the entity to save.
     * @return the persisted entity.
     */
    public ControleDiario update(ControleDiario controleDiario) {
        log.debug("Request to update ControleDiario : {}", controleDiario);
        return controleDiarioRepository.save(controleDiario);
    }

    /**
     * Partially update a controleDiario.
     *
     * @param controleDiario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ControleDiario> partialUpdate(ControleDiario controleDiario) {
        log.debug("Request to partially update ControleDiario : {}", controleDiario);

        return controleDiarioRepository
            .findById(controleDiario.getId())
            .map(existingControleDiario -> {
                if (controleDiario.getData() != null) {
                    existingControleDiario.setData(controleDiario.getData());
                }
                if (controleDiario.getCliente() != null) {
                    existingControleDiario.setCliente(controleDiario.getCliente());
                }
                if (controleDiario.getValorCompra() != null) {
                    existingControleDiario.setValorCompra(controleDiario.getValorCompra());
                }
                if (controleDiario.getValorPago() != null) {
                    existingControleDiario.setValorPago(controleDiario.getValorPago());
                }
                if (controleDiario.getSaldoDevedor() != null) {
                    existingControleDiario.setSaldoDevedor(controleDiario.getSaldoDevedor());
                }
                if (controleDiario.getRecebimento() != null) {
                    existingControleDiario.setRecebimento(controleDiario.getRecebimento());
                }
                if (controleDiario.getPagamento() != null) {
                    existingControleDiario.setPagamento(controleDiario.getPagamento());
                }
                if (controleDiario.getBanco() != null) {
                    existingControleDiario.setBanco(controleDiario.getBanco());
                }

                return existingControleDiario;
            })
            .map(controleDiarioRepository::save);
    }

    /**
     * Get all the controleDiarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ControleDiario> findAll(Pageable pageable) {
        log.debug("Request to get all ControleDiarios");
        return controleDiarioRepository.findAll(pageable);
    }

    /**
     * Get one controleDiario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ControleDiario> findOne(Long id) {
        log.debug("Request to get ControleDiario : {}", id);
        return controleDiarioRepository.findById(id);
    }

    /**
     * Delete the controleDiario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ControleDiario : {}", id);
        controleDiarioRepository.deleteById(id);
    }
}
