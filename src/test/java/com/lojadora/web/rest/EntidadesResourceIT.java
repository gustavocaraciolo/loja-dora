package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.Entidades;
import com.lojadora.repository.EntidadesRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link EntidadesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntidadesResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONE = 1;
    private static final Integer UPDATED_TELEFONE = 2;

    private static final String ENTITY_API_URL = "/api/entidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntidadesRepository entidadesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntidadesMockMvc;

    private Entidades entidades;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidades createEntity(EntityManager em) {
        Entidades entidades = new Entidades().nome(DEFAULT_NOME).endereco(DEFAULT_ENDERECO).telefone(DEFAULT_TELEFONE);
        return entidades;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidades createUpdatedEntity(EntityManager em) {
        Entidades entidades = new Entidades().nome(UPDATED_NOME).endereco(UPDATED_ENDERECO).telefone(UPDATED_TELEFONE);
        return entidades;
    }

    @BeforeEach
    public void initTest() {
        entidades = createEntity(em);
    }

    @Test
    @Transactional
    void createEntidades() throws Exception {
        int databaseSizeBeforeCreate = entidadesRepository.findAll().size();
        // Create the Entidades
        restEntidadesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidades)))
            .andExpect(status().isCreated());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeCreate + 1);
        Entidades testEntidades = entidadesList.get(entidadesList.size() - 1);
        assertThat(testEntidades.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEntidades.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testEntidades.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createEntidadesWithExistingId() throws Exception {
        // Create the Entidades with an existing ID
        entidades.setId(1L);

        int databaseSizeBeforeCreate = entidadesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidades)))
            .andExpect(status().isBadRequest());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntidades() throws Exception {
        // Initialize the database
        entidadesRepository.saveAndFlush(entidades);

        // Get all the entidadesList
        restEntidadesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidades.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }

    @Test
    @Transactional
    void getEntidades() throws Exception {
        // Initialize the database
        entidadesRepository.saveAndFlush(entidades);

        // Get the entidades
        restEntidadesMockMvc
            .perform(get(ENTITY_API_URL_ID, entidades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entidades.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }

    @Test
    @Transactional
    void getNonExistingEntidades() throws Exception {
        // Get the entidades
        restEntidadesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntidades() throws Exception {
        // Initialize the database
        entidadesRepository.saveAndFlush(entidades);

        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();

        // Update the entidades
        Entidades updatedEntidades = entidadesRepository.findById(entidades.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntidades are not directly saved in db
        em.detach(updatedEntidades);
        updatedEntidades.nome(UPDATED_NOME).endereco(UPDATED_ENDERECO).telefone(UPDATED_TELEFONE);

        restEntidadesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntidades.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntidades))
            )
            .andExpect(status().isOk());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
        Entidades testEntidades = entidadesList.get(entidadesList.size() - 1);
        assertThat(testEntidades.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEntidades.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEntidades.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingEntidades() throws Exception {
        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();
        entidades.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entidades.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entidades))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntidades() throws Exception {
        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();
        entidades.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entidades))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntidades() throws Exception {
        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();
        entidades.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidades)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntidadesWithPatch() throws Exception {
        // Initialize the database
        entidadesRepository.saveAndFlush(entidades);

        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();

        // Update the entidades using partial update
        Entidades partialUpdatedEntidades = new Entidades();
        partialUpdatedEntidades.setId(entidades.getId());

        partialUpdatedEntidades.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE);

        restEntidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntidades.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntidades))
            )
            .andExpect(status().isOk());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
        Entidades testEntidades = entidadesList.get(entidadesList.size() - 1);
        assertThat(testEntidades.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEntidades.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testEntidades.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateEntidadesWithPatch() throws Exception {
        // Initialize the database
        entidadesRepository.saveAndFlush(entidades);

        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();

        // Update the entidades using partial update
        Entidades partialUpdatedEntidades = new Entidades();
        partialUpdatedEntidades.setId(entidades.getId());

        partialUpdatedEntidades.nome(UPDATED_NOME).endereco(UPDATED_ENDERECO).telefone(UPDATED_TELEFONE);

        restEntidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntidades.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntidades))
            )
            .andExpect(status().isOk());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
        Entidades testEntidades = entidadesList.get(entidadesList.size() - 1);
        assertThat(testEntidades.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEntidades.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEntidades.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingEntidades() throws Exception {
        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();
        entidades.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entidades.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entidades))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntidades() throws Exception {
        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();
        entidades.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entidades))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntidades() throws Exception {
        int databaseSizeBeforeUpdate = entidadesRepository.findAll().size();
        entidades.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entidades))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidades in the database
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntidades() throws Exception {
        // Initialize the database
        entidadesRepository.saveAndFlush(entidades);

        int databaseSizeBeforeDelete = entidadesRepository.findAll().size();

        // Delete the entidades
        restEntidadesMockMvc
            .perform(delete(ENTITY_API_URL_ID, entidades.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entidades> entidadesList = entidadesRepository.findAll();
        assertThat(entidadesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
