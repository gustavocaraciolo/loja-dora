package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.FluxoCaixa;
import com.lojadora.domain.enumeration.Banco;
import com.lojadora.domain.enumeration.EntradaSaida;
import com.lojadora.domain.enumeration.FixoVariavel;
import com.lojadora.domain.enumeration.Pais;
import com.lojadora.repository.FluxoCaixaRepository;
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
 * Integration tests for the {@link FluxoCaixaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FluxoCaixaResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_SALDO = 1F;
    private static final Float UPDATED_SALDO = 2F;

    private static final Banco DEFAULT_BANCO = Banco.SOL;
    private static final Banco UPDATED_BANCO = Banco.CAIXA_ANGOLA;

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final FixoVariavel DEFAULT_FIXO_VARIAVEL = FixoVariavel.FIXO;
    private static final FixoVariavel UPDATED_FIXO_VARIAVEL = FixoVariavel.VARIAVEL;

    private static final EntradaSaida DEFAULT_ENTRADA_SAIDA = EntradaSaida.ENTRADA;
    private static final EntradaSaida UPDATED_ENTRADA_SAIDA = EntradaSaida.SAIDA;

    private static final Pais DEFAULT_PAIS = Pais.ANGOLA;
    private static final Pais UPDATED_PAIS = Pais.BRASIL;

    private static final String ENTITY_API_URL = "/api/fluxo-caixas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FluxoCaixaRepository fluxoCaixaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFluxoCaixaMockMvc;

    private FluxoCaixa fluxoCaixa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FluxoCaixa createEntity(EntityManager em) {
        FluxoCaixa fluxoCaixa = new FluxoCaixa()
            .data(DEFAULT_DATA)
            .saldo(DEFAULT_SALDO)
            .banco(DEFAULT_BANCO)
            .valor(DEFAULT_VALOR)
            .fixoVariavel(DEFAULT_FIXO_VARIAVEL)
            .entradaSaida(DEFAULT_ENTRADA_SAIDA)
            .pais(DEFAULT_PAIS);
        return fluxoCaixa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FluxoCaixa createUpdatedEntity(EntityManager em) {
        FluxoCaixa fluxoCaixa = new FluxoCaixa()
            .data(UPDATED_DATA)
            .saldo(UPDATED_SALDO)
            .banco(UPDATED_BANCO)
            .valor(UPDATED_VALOR)
            .fixoVariavel(UPDATED_FIXO_VARIAVEL)
            .entradaSaida(UPDATED_ENTRADA_SAIDA)
            .pais(UPDATED_PAIS);
        return fluxoCaixa;
    }

    @BeforeEach
    public void initTest() {
        fluxoCaixa = createEntity(em);
    }

    @Test
    @Transactional
    void createFluxoCaixa() throws Exception {
        int databaseSizeBeforeCreate = fluxoCaixaRepository.findAll().size();
        // Create the FluxoCaixa
        restFluxoCaixaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fluxoCaixa)))
            .andExpect(status().isCreated());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeCreate + 1);
        FluxoCaixa testFluxoCaixa = fluxoCaixaList.get(fluxoCaixaList.size() - 1);
        assertThat(testFluxoCaixa.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testFluxoCaixa.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testFluxoCaixa.getBanco()).isEqualTo(DEFAULT_BANCO);
        assertThat(testFluxoCaixa.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testFluxoCaixa.getFixoVariavel()).isEqualTo(DEFAULT_FIXO_VARIAVEL);
        assertThat(testFluxoCaixa.getEntradaSaida()).isEqualTo(DEFAULT_ENTRADA_SAIDA);
        assertThat(testFluxoCaixa.getPais()).isEqualTo(DEFAULT_PAIS);
    }

    @Test
    @Transactional
    void createFluxoCaixaWithExistingId() throws Exception {
        // Create the FluxoCaixa with an existing ID
        fluxoCaixa.setId(1L);

        int databaseSizeBeforeCreate = fluxoCaixaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFluxoCaixaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fluxoCaixa)))
            .andExpect(status().isBadRequest());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFluxoCaixas() throws Exception {
        // Initialize the database
        fluxoCaixaRepository.saveAndFlush(fluxoCaixa);

        // Get all the fluxoCaixaList
        restFluxoCaixaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fluxoCaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.doubleValue())))
            .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].fixoVariavel").value(hasItem(DEFAULT_FIXO_VARIAVEL.toString())))
            .andExpect(jsonPath("$.[*].entradaSaida").value(hasItem(DEFAULT_ENTRADA_SAIDA.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())));
    }

    @Test
    @Transactional
    void getFluxoCaixa() throws Exception {
        // Initialize the database
        fluxoCaixaRepository.saveAndFlush(fluxoCaixa);

        // Get the fluxoCaixa
        restFluxoCaixaMockMvc
            .perform(get(ENTITY_API_URL_ID, fluxoCaixa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fluxoCaixa.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO.doubleValue()))
            .andExpect(jsonPath("$.banco").value(DEFAULT_BANCO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.fixoVariavel").value(DEFAULT_FIXO_VARIAVEL.toString()))
            .andExpect(jsonPath("$.entradaSaida").value(DEFAULT_ENTRADA_SAIDA.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFluxoCaixa() throws Exception {
        // Get the fluxoCaixa
        restFluxoCaixaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFluxoCaixa() throws Exception {
        // Initialize the database
        fluxoCaixaRepository.saveAndFlush(fluxoCaixa);

        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();

        // Update the fluxoCaixa
        FluxoCaixa updatedFluxoCaixa = fluxoCaixaRepository.findById(fluxoCaixa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFluxoCaixa are not directly saved in db
        em.detach(updatedFluxoCaixa);
        updatedFluxoCaixa
            .data(UPDATED_DATA)
            .saldo(UPDATED_SALDO)
            .banco(UPDATED_BANCO)
            .valor(UPDATED_VALOR)
            .fixoVariavel(UPDATED_FIXO_VARIAVEL)
            .entradaSaida(UPDATED_ENTRADA_SAIDA)
            .pais(UPDATED_PAIS);

        restFluxoCaixaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFluxoCaixa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFluxoCaixa))
            )
            .andExpect(status().isOk());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
        FluxoCaixa testFluxoCaixa = fluxoCaixaList.get(fluxoCaixaList.size() - 1);
        assertThat(testFluxoCaixa.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testFluxoCaixa.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testFluxoCaixa.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testFluxoCaixa.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testFluxoCaixa.getFixoVariavel()).isEqualTo(UPDATED_FIXO_VARIAVEL);
        assertThat(testFluxoCaixa.getEntradaSaida()).isEqualTo(UPDATED_ENTRADA_SAIDA);
        assertThat(testFluxoCaixa.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void putNonExistingFluxoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();
        fluxoCaixa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxoCaixaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fluxoCaixa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fluxoCaixa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFluxoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();
        fluxoCaixa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoCaixaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fluxoCaixa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFluxoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();
        fluxoCaixa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoCaixaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fluxoCaixa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFluxoCaixaWithPatch() throws Exception {
        // Initialize the database
        fluxoCaixaRepository.saveAndFlush(fluxoCaixa);

        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();

        // Update the fluxoCaixa using partial update
        FluxoCaixa partialUpdatedFluxoCaixa = new FluxoCaixa();
        partialUpdatedFluxoCaixa.setId(fluxoCaixa.getId());

        partialUpdatedFluxoCaixa.banco(UPDATED_BANCO).valor(UPDATED_VALOR).fixoVariavel(UPDATED_FIXO_VARIAVEL);

        restFluxoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFluxoCaixa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFluxoCaixa))
            )
            .andExpect(status().isOk());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
        FluxoCaixa testFluxoCaixa = fluxoCaixaList.get(fluxoCaixaList.size() - 1);
        assertThat(testFluxoCaixa.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testFluxoCaixa.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testFluxoCaixa.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testFluxoCaixa.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testFluxoCaixa.getFixoVariavel()).isEqualTo(UPDATED_FIXO_VARIAVEL);
        assertThat(testFluxoCaixa.getEntradaSaida()).isEqualTo(DEFAULT_ENTRADA_SAIDA);
        assertThat(testFluxoCaixa.getPais()).isEqualTo(DEFAULT_PAIS);
    }

    @Test
    @Transactional
    void fullUpdateFluxoCaixaWithPatch() throws Exception {
        // Initialize the database
        fluxoCaixaRepository.saveAndFlush(fluxoCaixa);

        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();

        // Update the fluxoCaixa using partial update
        FluxoCaixa partialUpdatedFluxoCaixa = new FluxoCaixa();
        partialUpdatedFluxoCaixa.setId(fluxoCaixa.getId());

        partialUpdatedFluxoCaixa
            .data(UPDATED_DATA)
            .saldo(UPDATED_SALDO)
            .banco(UPDATED_BANCO)
            .valor(UPDATED_VALOR)
            .fixoVariavel(UPDATED_FIXO_VARIAVEL)
            .entradaSaida(UPDATED_ENTRADA_SAIDA)
            .pais(UPDATED_PAIS);

        restFluxoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFluxoCaixa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFluxoCaixa))
            )
            .andExpect(status().isOk());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
        FluxoCaixa testFluxoCaixa = fluxoCaixaList.get(fluxoCaixaList.size() - 1);
        assertThat(testFluxoCaixa.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testFluxoCaixa.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testFluxoCaixa.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testFluxoCaixa.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testFluxoCaixa.getFixoVariavel()).isEqualTo(UPDATED_FIXO_VARIAVEL);
        assertThat(testFluxoCaixa.getEntradaSaida()).isEqualTo(UPDATED_ENTRADA_SAIDA);
        assertThat(testFluxoCaixa.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void patchNonExistingFluxoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();
        fluxoCaixa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fluxoCaixa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fluxoCaixa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFluxoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();
        fluxoCaixa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fluxoCaixa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFluxoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fluxoCaixaRepository.findAll().size();
        fluxoCaixa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fluxoCaixa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FluxoCaixa in the database
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFluxoCaixa() throws Exception {
        // Initialize the database
        fluxoCaixaRepository.saveAndFlush(fluxoCaixa);

        int databaseSizeBeforeDelete = fluxoCaixaRepository.findAll().size();

        // Delete the fluxoCaixa
        restFluxoCaixaMockMvc
            .perform(delete(ENTITY_API_URL_ID, fluxoCaixa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FluxoCaixa> fluxoCaixaList = fluxoCaixaRepository.findAll();
        assertThat(fluxoCaixaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
