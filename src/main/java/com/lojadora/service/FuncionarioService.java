package com.lojadora.service;

import com.lojadora.domain.Funcionario;
import com.lojadora.repository.FuncionarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lojadora.domain.Funcionario}.
 */
@Service
@Transactional
public class FuncionarioService {

    private final Logger log = LoggerFactory.getLogger(FuncionarioService.class);

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Save a funcionario.
     *
     * @param funcionario the entity to save.
     * @return the persisted entity.
     */
    public Funcionario save(Funcionario funcionario) {
        log.debug("Request to save Funcionario : {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }

    /**
     * Update a funcionario.
     *
     * @param funcionario the entity to save.
     * @return the persisted entity.
     */
    public Funcionario update(Funcionario funcionario) {
        log.debug("Request to update Funcionario : {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }

    /**
     * Partially update a funcionario.
     *
     * @param funcionario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Funcionario> partialUpdate(Funcionario funcionario) {
        log.debug("Request to partially update Funcionario : {}", funcionario);

        return funcionarioRepository
            .findById(funcionario.getId())
            .map(existingFuncionario -> {
                if (funcionario.getPrimeiroNome() != null) {
                    existingFuncionario.setPrimeiroNome(funcionario.getPrimeiroNome());
                }
                if (funcionario.getUltimoNome() != null) {
                    existingFuncionario.setUltimoNome(funcionario.getUltimoNome());
                }
                if (funcionario.getEnderecoLinha1() != null) {
                    existingFuncionario.setEnderecoLinha1(funcionario.getEnderecoLinha1());
                }
                if (funcionario.getEnderecoLinha2() != null) {
                    existingFuncionario.setEnderecoLinha2(funcionario.getEnderecoLinha2());
                }
                if (funcionario.getDataInicio() != null) {
                    existingFuncionario.setDataInicio(funcionario.getDataInicio());
                }
                if (funcionario.getTelefone() != null) {
                    existingFuncionario.setTelefone(funcionario.getTelefone());
                }
                if (funcionario.getTelefoneEmergencial() != null) {
                    existingFuncionario.setTelefoneEmergencial(funcionario.getTelefoneEmergencial());
                }
                if (funcionario.getEmail() != null) {
                    existingFuncionario.setEmail(funcionario.getEmail());
                }
                if (funcionario.getBanco() != null) {
                    existingFuncionario.setBanco(funcionario.getBanco());
                }
                if (funcionario.getIban() != null) {
                    existingFuncionario.setIban(funcionario.getIban());
                }

                return existingFuncionario;
            })
            .map(funcionarioRepository::save);
    }

    /**
     * Get all the funcionarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Funcionario> findAll(Pageable pageable) {
        log.debug("Request to get all Funcionarios");
        return funcionarioRepository.findAll(pageable);
    }

    /**
     * Get one funcionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Funcionario> findOne(Long id) {
        log.debug("Request to get Funcionario : {}", id);
        return funcionarioRepository.findById(id);
    }

    /**
     * Delete the funcionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Funcionario : {}", id);
        funcionarioRepository.deleteById(id);
    }
}
