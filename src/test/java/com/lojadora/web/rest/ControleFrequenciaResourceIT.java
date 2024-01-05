package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.ControleFrequencia;
import com.lojadora.repository.ControleFrequenciaRepository;
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
 * Integration tests for the {@link ControleFrequenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ControleFrequenciaResourceIT {

    private static final LocalDate DEFAULT_DATA_TRABALHO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_TRABALHO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/controle-frequencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ControleFrequenciaRepository controleFrequenciaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControleFrequenciaMockMvc;

    private ControleFrequencia controleFrequencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleFrequencia createEntity(EntityManager em) {
        ControleFrequencia controleFrequencia = new ControleFrequencia().dataTrabalho(DEFAULT_DATA_TRABALHO);
        return controleFrequencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleFrequencia createUpdatedEntity(EntityManager em) {
        ControleFrequencia controleFrequencia = new ControleFrequencia().dataTrabalho(UPDATED_DATA_TRABALHO);
        return controleFrequencia;
    }

    @BeforeEach
    public void initTest() {
        controleFrequencia = createEntity(em);
    }

    @Test
    @Transactional
    void createControleFrequencia() throws Exception {
        int databaseSizeBeforeCreate = controleFrequenciaRepository.findAll().size();
        // Create the ControleFrequencia
        restControleFrequenciaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isCreated());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeCreate + 1);
        ControleFrequencia testControleFrequencia = controleFrequenciaList.get(controleFrequenciaList.size() - 1);
        assertThat(testControleFrequencia.getDataTrabalho()).isEqualTo(DEFAULT_DATA_TRABALHO);
    }

    @Test
    @Transactional
    void createControleFrequenciaWithExistingId() throws Exception {
        // Create the ControleFrequencia with an existing ID
        controleFrequencia.setId(1L);

        int databaseSizeBeforeCreate = controleFrequenciaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleFrequenciaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllControleFrequencias() throws Exception {
        // Initialize the database
        controleFrequenciaRepository.saveAndFlush(controleFrequencia);

        // Get all the controleFrequenciaList
        restControleFrequenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleFrequencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataTrabalho").value(hasItem(DEFAULT_DATA_TRABALHO.toString())));
    }

    @Test
    @Transactional
    void getControleFrequencia() throws Exception {
        // Initialize the database
        controleFrequenciaRepository.saveAndFlush(controleFrequencia);

        // Get the controleFrequencia
        restControleFrequenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, controleFrequencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controleFrequencia.getId().intValue()))
            .andExpect(jsonPath("$.dataTrabalho").value(DEFAULT_DATA_TRABALHO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingControleFrequencia() throws Exception {
        // Get the controleFrequencia
        restControleFrequenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingControleFrequencia() throws Exception {
        // Initialize the database
        controleFrequenciaRepository.saveAndFlush(controleFrequencia);

        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();

        // Update the controleFrequencia
        ControleFrequencia updatedControleFrequencia = controleFrequenciaRepository.findById(controleFrequencia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedControleFrequencia are not directly saved in db
        em.detach(updatedControleFrequencia);
        updatedControleFrequencia.dataTrabalho(UPDATED_DATA_TRABALHO);

        restControleFrequenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedControleFrequencia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedControleFrequencia))
            )
            .andExpect(status().isOk());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
        ControleFrequencia testControleFrequencia = controleFrequenciaList.get(controleFrequenciaList.size() - 1);
        assertThat(testControleFrequencia.getDataTrabalho()).isEqualTo(UPDATED_DATA_TRABALHO);
    }

    @Test
    @Transactional
    void putNonExistingControleFrequencia() throws Exception {
        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();
        controleFrequencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleFrequenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controleFrequencia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControleFrequencia() throws Exception {
        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();
        controleFrequencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleFrequenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControleFrequencia() throws Exception {
        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();
        controleFrequencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleFrequenciaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControleFrequenciaWithPatch() throws Exception {
        // Initialize the database
        controleFrequenciaRepository.saveAndFlush(controleFrequencia);

        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();

        // Update the controleFrequencia using partial update
        ControleFrequencia partialUpdatedControleFrequencia = new ControleFrequencia();
        partialUpdatedControleFrequencia.setId(controleFrequencia.getId());

        partialUpdatedControleFrequencia.dataTrabalho(UPDATED_DATA_TRABALHO);

        restControleFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleFrequencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleFrequencia))
            )
            .andExpect(status().isOk());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
        ControleFrequencia testControleFrequencia = controleFrequenciaList.get(controleFrequenciaList.size() - 1);
        assertThat(testControleFrequencia.getDataTrabalho()).isEqualTo(UPDATED_DATA_TRABALHO);
    }

    @Test
    @Transactional
    void fullUpdateControleFrequenciaWithPatch() throws Exception {
        // Initialize the database
        controleFrequenciaRepository.saveAndFlush(controleFrequencia);

        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();

        // Update the controleFrequencia using partial update
        ControleFrequencia partialUpdatedControleFrequencia = new ControleFrequencia();
        partialUpdatedControleFrequencia.setId(controleFrequencia.getId());

        partialUpdatedControleFrequencia.dataTrabalho(UPDATED_DATA_TRABALHO);

        restControleFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleFrequencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleFrequencia))
            )
            .andExpect(status().isOk());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
        ControleFrequencia testControleFrequencia = controleFrequenciaList.get(controleFrequenciaList.size() - 1);
        assertThat(testControleFrequencia.getDataTrabalho()).isEqualTo(UPDATED_DATA_TRABALHO);
    }

    @Test
    @Transactional
    void patchNonExistingControleFrequencia() throws Exception {
        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();
        controleFrequencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controleFrequencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControleFrequencia() throws Exception {
        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();
        controleFrequencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControleFrequencia() throws Exception {
        int databaseSizeBeforeUpdate = controleFrequenciaRepository.findAll().size();
        controleFrequencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleFrequencia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleFrequencia in the database
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControleFrequencia() throws Exception {
        // Initialize the database
        controleFrequenciaRepository.saveAndFlush(controleFrequencia);

        int databaseSizeBeforeDelete = controleFrequenciaRepository.findAll().size();

        // Delete the controleFrequencia
        restControleFrequenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, controleFrequencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControleFrequencia> controleFrequenciaList = controleFrequenciaRepository.findAll();
        assertThat(controleFrequenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
