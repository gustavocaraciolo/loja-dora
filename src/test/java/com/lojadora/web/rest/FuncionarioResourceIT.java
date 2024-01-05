package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.Funcionario;
import com.lojadora.repository.FuncionarioRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FuncionarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuncionarioResourceIT {

    private static final String DEFAULT_PRIMEIRO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMEIRO_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ULTIMO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_ULTIMO_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_LINHA_1 = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_LINHA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_LINHA_2 = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_LINHA_2 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_EMERGENCIAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_EMERGENCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BANCO = "AAAAAAAAAA";
    private static final String UPDATED_BANCO = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .primeiroNome(DEFAULT_PRIMEIRO_NOME)
            .ultimoNome(DEFAULT_ULTIMO_NOME)
            .enderecoLinha1(DEFAULT_ENDERECO_LINHA_1)
            .enderecoLinha2(DEFAULT_ENDERECO_LINHA_2)
            .dataInicio(DEFAULT_DATA_INICIO)
            .telefone(DEFAULT_TELEFONE)
            .telefoneEmergencial(DEFAULT_TELEFONE_EMERGENCIAL)
            .email(DEFAULT_EMAIL)
            .banco(DEFAULT_BANCO)
            .iban(DEFAULT_IBAN);
        return funcionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createUpdatedEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .ultimoNome(UPDATED_ULTIMO_NOME)
            .enderecoLinha1(UPDATED_ENDERECO_LINHA_1)
            .enderecoLinha2(UPDATED_ENDERECO_LINHA_2)
            .dataInicio(UPDATED_DATA_INICIO)
            .telefone(UPDATED_TELEFONE)
            .telefoneEmergencial(UPDATED_TELEFONE_EMERGENCIAL)
            .email(UPDATED_EMAIL)
            .banco(UPDATED_BANCO)
            .iban(UPDATED_IBAN);
        return funcionario;
    }

    @BeforeEach
    public void initTest() {
        funcionario = createEntity(em);
    }

    @Test
    @Transactional
    void createFuncionario() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();
        // Create the Funcionario
        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getPrimeiroNome()).isEqualTo(DEFAULT_PRIMEIRO_NOME);
        assertThat(testFuncionario.getUltimoNome()).isEqualTo(DEFAULT_ULTIMO_NOME);
        assertThat(testFuncionario.getEnderecoLinha1()).isEqualTo(DEFAULT_ENDERECO_LINHA_1);
        assertThat(testFuncionario.getEnderecoLinha2()).isEqualTo(DEFAULT_ENDERECO_LINHA_2);
        assertThat(testFuncionario.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testFuncionario.getTelefoneEmergencial()).isEqualTo(DEFAULT_TELEFONE_EMERGENCIAL);
        assertThat(testFuncionario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFuncionario.getBanco()).isEqualTo(DEFAULT_BANCO);
        assertThat(testFuncionario.getIban()).isEqualTo(DEFAULT_IBAN);
    }

    @Test
    @Transactional
    void createFuncionarioWithExistingId() throws Exception {
        // Create the Funcionario with an existing ID
        funcionario.setId(1L);

        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrimeiroNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setPrimeiroNome(null);

        // Create the Funcionario, which fails.

        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUltimoNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setUltimoNome(null);

        // Create the Funcionario, which fails.

        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME)))
            .andExpect(jsonPath("$.[*].ultimoNome").value(hasItem(DEFAULT_ULTIMO_NOME)))
            .andExpect(jsonPath("$.[*].enderecoLinha1").value(hasItem(DEFAULT_ENDERECO_LINHA_1)))
            .andExpect(jsonPath("$.[*].enderecoLinha2").value(hasItem(DEFAULT_ENDERECO_LINHA_2)))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].telefoneEmergencial").value(hasItem(DEFAULT_TELEFONE_EMERGENCIAL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)));
    }

    @Test
    @Transactional
    void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.primeiroNome").value(DEFAULT_PRIMEIRO_NOME))
            .andExpect(jsonPath("$.ultimoNome").value(DEFAULT_ULTIMO_NOME))
            .andExpect(jsonPath("$.enderecoLinha1").value(DEFAULT_ENDERECO_LINHA_1))
            .andExpect(jsonPath("$.enderecoLinha2").value(DEFAULT_ENDERECO_LINHA_2))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.telefoneEmergencial").value(DEFAULT_TELEFONE_EMERGENCIAL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.banco").value(DEFAULT_BANCO))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN));
    }

    @Test
    @Transactional
    void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .ultimoNome(UPDATED_ULTIMO_NOME)
            .enderecoLinha1(UPDATED_ENDERECO_LINHA_1)
            .enderecoLinha2(UPDATED_ENDERECO_LINHA_2)
            .dataInicio(UPDATED_DATA_INICIO)
            .telefone(UPDATED_TELEFONE)
            .telefoneEmergencial(UPDATED_TELEFONE_EMERGENCIAL)
            .email(UPDATED_EMAIL)
            .banco(UPDATED_BANCO)
            .iban(UPDATED_IBAN);

        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testFuncionario.getUltimoNome()).isEqualTo(UPDATED_ULTIMO_NOME);
        assertThat(testFuncionario.getEnderecoLinha1()).isEqualTo(UPDATED_ENDERECO_LINHA_1);
        assertThat(testFuncionario.getEnderecoLinha2()).isEqualTo(UPDATED_ENDERECO_LINHA_2);
        assertThat(testFuncionario.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testFuncionario.getTelefoneEmergencial()).isEqualTo(UPDATED_TELEFONE_EMERGENCIAL);
        assertThat(testFuncionario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFuncionario.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testFuncionario.getIban()).isEqualTo(UPDATED_IBAN);
    }

    @Test
    @Transactional
    void putNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .ultimoNome(UPDATED_ULTIMO_NOME)
            .dataInicio(UPDATED_DATA_INICIO)
            .banco(UPDATED_BANCO);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testFuncionario.getUltimoNome()).isEqualTo(UPDATED_ULTIMO_NOME);
        assertThat(testFuncionario.getEnderecoLinha1()).isEqualTo(DEFAULT_ENDERECO_LINHA_1);
        assertThat(testFuncionario.getEnderecoLinha2()).isEqualTo(DEFAULT_ENDERECO_LINHA_2);
        assertThat(testFuncionario.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testFuncionario.getTelefoneEmergencial()).isEqualTo(DEFAULT_TELEFONE_EMERGENCIAL);
        assertThat(testFuncionario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFuncionario.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testFuncionario.getIban()).isEqualTo(DEFAULT_IBAN);
    }

    @Test
    @Transactional
    void fullUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .ultimoNome(UPDATED_ULTIMO_NOME)
            .enderecoLinha1(UPDATED_ENDERECO_LINHA_1)
            .enderecoLinha2(UPDATED_ENDERECO_LINHA_2)
            .dataInicio(UPDATED_DATA_INICIO)
            .telefone(UPDATED_TELEFONE)
            .telefoneEmergencial(UPDATED_TELEFONE_EMERGENCIAL)
            .email(UPDATED_EMAIL)
            .banco(UPDATED_BANCO)
            .iban(UPDATED_IBAN);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testFuncionario.getUltimoNome()).isEqualTo(UPDATED_ULTIMO_NOME);
        assertThat(testFuncionario.getEnderecoLinha1()).isEqualTo(UPDATED_ENDERECO_LINHA_1);
        assertThat(testFuncionario.getEnderecoLinha2()).isEqualTo(UPDATED_ENDERECO_LINHA_2);
        assertThat(testFuncionario.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testFuncionario.getTelefoneEmergencial()).isEqualTo(UPDATED_TELEFONE_EMERGENCIAL);
        assertThat(testFuncionario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFuncionario.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testFuncionario.getIban()).isEqualTo(UPDATED_IBAN);
    }

    @Test
    @Transactional
    void patchNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeDelete = funcionarioRepository.findAll().size();

        // Delete the funcionario
        restFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
