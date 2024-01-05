package com.lojadora.service;

import com.lojadora.domain.ControlePagamentoFuncionario;
import com.lojadora.repository.ControlePagamentoFuncionarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.ControlePagamentoFuncionario}.
 */
@Service
@Transactional
public class ControlePagamentoFuncionarioService {

    private final Logger log = LoggerFactory.getLogger(ControlePagamentoFuncionarioService.class);

    private final ControlePagamentoFuncionarioRepository controlePagamentoFuncionarioRepository;

    public ControlePagamentoFuncionarioService(ControlePagamentoFuncionarioRepository controlePagamentoFuncionarioRepository) {
        this.controlePagamentoFuncionarioRepository = controlePagamentoFuncionarioRepository;
    }

    /**
     * Save a controlePagamentoFuncionario.
     *
     * @param controlePagamentoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public ControlePagamentoFuncionario save(ControlePagamentoFuncionario controlePagamentoFuncionario) {
        log.debug("Request to save ControlePagamentoFuncionario : {}", controlePagamentoFuncionario);
        return controlePagamentoFuncionarioRepository.save(controlePagamentoFuncionario);
    }

    /**
     * Update a controlePagamentoFuncionario.
     *
     * @param controlePagamentoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public ControlePagamentoFuncionario update(ControlePagamentoFuncionario controlePagamentoFuncionario) {
        log.debug("Request to update ControlePagamentoFuncionario : {}", controlePagamentoFuncionario);
        return controlePagamentoFuncionarioRepository.save(controlePagamentoFuncionario);
    }

    /**
     * Partially update a controlePagamentoFuncionario.
     *
     * @param controlePagamentoFuncionario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ControlePagamentoFuncionario> partialUpdate(ControlePagamentoFuncionario controlePagamentoFuncionario) {
        log.debug("Request to partially update ControlePagamentoFuncionario : {}", controlePagamentoFuncionario);

        return controlePagamentoFuncionarioRepository
            .findById(controlePagamentoFuncionario.getId())
            .map(existingControlePagamentoFuncionario -> {
                if (controlePagamentoFuncionario.getData() != null) {
                    existingControlePagamentoFuncionario.setData(controlePagamentoFuncionario.getData());
                }
                if (controlePagamentoFuncionario.getSalario() != null) {
                    existingControlePagamentoFuncionario.setSalario(controlePagamentoFuncionario.getSalario());
                }
                if (controlePagamentoFuncionario.getBeneficio() != null) {
                    existingControlePagamentoFuncionario.setBeneficio(controlePagamentoFuncionario.getBeneficio());
                }
                if (controlePagamentoFuncionario.getComissao() != null) {
                    existingControlePagamentoFuncionario.setComissao(controlePagamentoFuncionario.getComissao());
                }
                if (controlePagamentoFuncionario.getFerias() != null) {
                    existingControlePagamentoFuncionario.setFerias(controlePagamentoFuncionario.getFerias());
                }
                if (controlePagamentoFuncionario.getAdiantamento() != null) {
                    existingControlePagamentoFuncionario.setAdiantamento(controlePagamentoFuncionario.getAdiantamento());
                }
                if (controlePagamentoFuncionario.getTotal() != null) {
                    existingControlePagamentoFuncionario.setTotal(controlePagamentoFuncionario.getTotal());
                }

                return existingControlePagamentoFuncionario;
            })
            .map(controlePagamentoFuncionarioRepository::save);
    }

    /**
     * Get all the controlePagamentoFuncionarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ControlePagamentoFuncionario> findAll(Pageable pageable) {
        log.debug("Request to get all ControlePagamentoFuncionarios");
        return controlePagamentoFuncionarioRepository.findAll(pageable);
    }

    /**
     * Get one controlePagamentoFuncionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ControlePagamentoFuncionario> findOne(Long id) {
        log.debug("Request to get ControlePagamentoFuncionario : {}", id);
        return controlePagamentoFuncionarioRepository.findById(id);
    }

    /**
     * Delete the controlePagamentoFuncionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ControlePagamentoFuncionario : {}", id);
        controlePagamentoFuncionarioRepository.deleteById(id);
    }
}
