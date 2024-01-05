package com.lojadora.service;

import com.lojadora.domain.FluxoCaixa;
import com.lojadora.repository.FluxoCaixaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.FluxoCaixa}.
 */
@Service
@Transactional
public class FluxoCaixaService {

    private final Logger log = LoggerFactory.getLogger(FluxoCaixaService.class);

    private final FluxoCaixaRepository fluxoCaixaRepository;

    public FluxoCaixaService(FluxoCaixaRepository fluxoCaixaRepository) {
        this.fluxoCaixaRepository = fluxoCaixaRepository;
    }

    /**
     * Save a fluxoCaixa.
     *
     * @param fluxoCaixa the entity to save.
     * @return the persisted entity.
     */
    public FluxoCaixa save(FluxoCaixa fluxoCaixa) {
        log.debug("Request to save FluxoCaixa : {}", fluxoCaixa);
        return fluxoCaixaRepository.save(fluxoCaixa);
    }

    /**
     * Update a fluxoCaixa.
     *
     * @param fluxoCaixa the entity to save.
     * @return the persisted entity.
     */
    public FluxoCaixa update(FluxoCaixa fluxoCaixa) {
        log.debug("Request to update FluxoCaixa : {}", fluxoCaixa);
        return fluxoCaixaRepository.save(fluxoCaixa);
    }

    /**
     * Partially update a fluxoCaixa.
     *
     * @param fluxoCaixa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FluxoCaixa> partialUpdate(FluxoCaixa fluxoCaixa) {
        log.debug("Request to partially update FluxoCaixa : {}", fluxoCaixa);

        return fluxoCaixaRepository
            .findById(fluxoCaixa.getId())
            .map(existingFluxoCaixa -> {
                if (fluxoCaixa.getData() != null) {
                    existingFluxoCaixa.setData(fluxoCaixa.getData());
                }
                if (fluxoCaixa.getSaldo() != null) {
                    existingFluxoCaixa.setSaldo(fluxoCaixa.getSaldo());
                }
                if (fluxoCaixa.getBanco() != null) {
                    existingFluxoCaixa.setBanco(fluxoCaixa.getBanco());
                }
                if (fluxoCaixa.getValor() != null) {
                    existingFluxoCaixa.setValor(fluxoCaixa.getValor());
                }
                if (fluxoCaixa.getFixoVariavel() != null) {
                    existingFluxoCaixa.setFixoVariavel(fluxoCaixa.getFixoVariavel());
                }
                if (fluxoCaixa.getEntradaSaida() != null) {
                    existingFluxoCaixa.setEntradaSaida(fluxoCaixa.getEntradaSaida());
                }
                if (fluxoCaixa.getPais() != null) {
                    existingFluxoCaixa.setPais(fluxoCaixa.getPais());
                }

                return existingFluxoCaixa;
            })
            .map(fluxoCaixaRepository::save);
    }

    /**
     * Get all the fluxoCaixas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FluxoCaixa> findAll(Pageable pageable) {
        log.debug("Request to get all FluxoCaixas");
        return fluxoCaixaRepository.findAll(pageable);
    }

    /**
     * Get one fluxoCaixa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FluxoCaixa> findOne(Long id) {
        log.debug("Request to get FluxoCaixa : {}", id);
        return fluxoCaixaRepository.findById(id);
    }

    /**
     * Delete the fluxoCaixa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FluxoCaixa : {}", id);
        fluxoCaixaRepository.deleteById(id);
    }
}
