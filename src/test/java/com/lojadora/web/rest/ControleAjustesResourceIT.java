package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.ControleAjustes;
import com.lojadora.domain.enumeration.Receita;
import com.lojadora.repository.ControleAjustesRepository;
import com.lojadora.service.ControleAjustesService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ControleAjustesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ControleAjustesResourceIT {

    private static final LocalDate DEFAULT_DATA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ENTREGA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_RECEBIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_RECEBIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QTD_PECAS = 1;
    private static final Integer UPDATED_QTD_PECAS = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final Receita DEFAULT_RECEITA = Receita.DORA;
    private static final Receita UPDATED_RECEITA = Receita.CLIENTE;

    private static final String ENTITY_API_URL = "/api/controle-ajustes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ControleAjustesRepository controleAjustesRepository;

    @Mock
    private ControleAjustesRepository controleAjustesRepositoryMock;

    @Mock
    private ControleAjustesService controleAjustesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControleAjustesMockMvc;

    private ControleAjustes controleAjustes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleAjustes createEntity(EntityManager em) {
        ControleAjustes controleAjustes = new ControleAjustes()
            .dataEntrega(DEFAULT_DATA_ENTREGA)
            .dataRecebimento(DEFAULT_DATA_RECEBIMENTO)
            .qtdPecas(DEFAULT_QTD_PECAS)
            .descricao(DEFAULT_DESCRICAO)
            .valor(DEFAULT_VALOR)
            .receita(DEFAULT_RECEITA);
        return controleAjustes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleAjustes createUpdatedEntity(EntityManager em) {
        ControleAjustes controleAjustes = new ControleAjustes()
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataRecebimento(UPDATED_DATA_RECEBIMENTO)
            .qtdPecas(UPDATED_QTD_PECAS)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .receita(UPDATED_RECEITA);
        return controleAjustes;
    }

    @BeforeEach
    public void initTest() {
        controleAjustes = createEntity(em);
    }

    @Test
    @Transactional
    void createControleAjustes() throws Exception {
        int databaseSizeBeforeCreate = controleAjustesRepository.findAll().size();
        // Create the ControleAjustes
        restControleAjustesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isCreated());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeCreate + 1);
        ControleAjustes testControleAjustes = controleAjustesList.get(controleAjustesList.size() - 1);
        assertThat(testControleAjustes.getDataEntrega()).isEqualTo(DEFAULT_DATA_ENTREGA);
        assertThat(testControleAjustes.getDataRecebimento()).isEqualTo(DEFAULT_DATA_RECEBIMENTO);
        assertThat(testControleAjustes.getQtdPecas()).isEqualTo(DEFAULT_QTD_PECAS);
        assertThat(testControleAjustes.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testControleAjustes.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testControleAjustes.getReceita()).isEqualTo(DEFAULT_RECEITA);
    }

    @Test
    @Transactional
    void createControleAjustesWithExistingId() throws Exception {
        // Create the ControleAjustes with an existing ID
        controleAjustes.setId(1L);

        int databaseSizeBeforeCreate = controleAjustesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleAjustesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllControleAjustes() throws Exception {
        // Initialize the database
        controleAjustesRepository.saveAndFlush(controleAjustes);

        // Get all the controleAjustesList
        restControleAjustesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleAjustes.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataEntrega").value(hasItem(DEFAULT_DATA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].dataRecebimento").value(hasItem(DEFAULT_DATA_RECEBIMENTO.toString())))
            .andExpect(jsonPath("$.[*].qtdPecas").value(hasItem(DEFAULT_QTD_PECAS)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].receita").value(hasItem(DEFAULT_RECEITA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControleAjustesWithEagerRelationshipsIsEnabled() throws Exception {
        when(controleAjustesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControleAjustesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(controleAjustesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControleAjustesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(controleAjustesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControleAjustesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(controleAjustesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getControleAjustes() throws Exception {
        // Initialize the database
        controleAjustesRepository.saveAndFlush(controleAjustes);

        // Get the controleAjustes
        restControleAjustesMockMvc
            .perform(get(ENTITY_API_URL_ID, controleAjustes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controleAjustes.getId().intValue()))
            .andExpect(jsonPath("$.dataEntrega").value(DEFAULT_DATA_ENTREGA.toString()))
            .andExpect(jsonPath("$.dataRecebimento").value(DEFAULT_DATA_RECEBIMENTO.toString()))
            .andExpect(jsonPath("$.qtdPecas").value(DEFAULT_QTD_PECAS))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.receita").value(DEFAULT_RECEITA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingControleAjustes() throws Exception {
        // Get the controleAjustes
        restControleAjustesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingControleAjustes() throws Exception {
        // Initialize the database
        controleAjustesRepository.saveAndFlush(controleAjustes);

        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();

        // Update the controleAjustes
        ControleAjustes updatedControleAjustes = controleAjustesRepository.findById(controleAjustes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedControleAjustes are not directly saved in db
        em.detach(updatedControleAjustes);
        updatedControleAjustes
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataRecebimento(UPDATED_DATA_RECEBIMENTO)
            .qtdPecas(UPDATED_QTD_PECAS)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .receita(UPDATED_RECEITA);

        restControleAjustesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedControleAjustes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedControleAjustes))
            )
            .andExpect(status().isOk());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
        ControleAjustes testControleAjustes = controleAjustesList.get(controleAjustesList.size() - 1);
        assertThat(testControleAjustes.getDataEntrega()).isEqualTo(UPDATED_DATA_ENTREGA);
        assertThat(testControleAjustes.getDataRecebimento()).isEqualTo(UPDATED_DATA_RECEBIMENTO);
        assertThat(testControleAjustes.getQtdPecas()).isEqualTo(UPDATED_QTD_PECAS);
        assertThat(testControleAjustes.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testControleAjustes.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testControleAjustes.getReceita()).isEqualTo(UPDATED_RECEITA);
    }

    @Test
    @Transactional
    void putNonExistingControleAjustes() throws Exception {
        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();
        controleAjustes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleAjustesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controleAjustes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControleAjustes() throws Exception {
        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();
        controleAjustes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAjustesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControleAjustes() throws Exception {
        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();
        controleAjustes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAjustesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControleAjustesWithPatch() throws Exception {
        // Initialize the database
        controleAjustesRepository.saveAndFlush(controleAjustes);

        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();

        // Update the controleAjustes using partial update
        ControleAjustes partialUpdatedControleAjustes = new ControleAjustes();
        partialUpdatedControleAjustes.setId(controleAjustes.getId());

        partialUpdatedControleAjustes.dataEntrega(UPDATED_DATA_ENTREGA).qtdPecas(UPDATED_QTD_PECAS).descricao(UPDATED_DESCRICAO);

        restControleAjustesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleAjustes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleAjustes))
            )
            .andExpect(status().isOk());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
        ControleAjustes testControleAjustes = controleAjustesList.get(controleAjustesList.size() - 1);
        assertThat(testControleAjustes.getDataEntrega()).isEqualTo(UPDATED_DATA_ENTREGA);
        assertThat(testControleAjustes.getDataRecebimento()).isEqualTo(DEFAULT_DATA_RECEBIMENTO);
        assertThat(testControleAjustes.getQtdPecas()).isEqualTo(UPDATED_QTD_PECAS);
        assertThat(testControleAjustes.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testControleAjustes.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testControleAjustes.getReceita()).isEqualTo(DEFAULT_RECEITA);
    }

    @Test
    @Transactional
    void fullUpdateControleAjustesWithPatch() throws Exception {
        // Initialize the database
        controleAjustesRepository.saveAndFlush(controleAjustes);

        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();

        // Update the controleAjustes using partial update
        ControleAjustes partialUpdatedControleAjustes = new ControleAjustes();
        partialUpdatedControleAjustes.setId(controleAjustes.getId());

        partialUpdatedControleAjustes
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataRecebimento(UPDATED_DATA_RECEBIMENTO)
            .qtdPecas(UPDATED_QTD_PECAS)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .receita(UPDATED_RECEITA);

        restControleAjustesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleAjustes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleAjustes))
            )
            .andExpect(status().isOk());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
        ControleAjustes testControleAjustes = controleAjustesList.get(controleAjustesList.size() - 1);
        assertThat(testControleAjustes.getDataEntrega()).isEqualTo(UPDATED_DATA_ENTREGA);
        assertThat(testControleAjustes.getDataRecebimento()).isEqualTo(UPDATED_DATA_RECEBIMENTO);
        assertThat(testControleAjustes.getQtdPecas()).isEqualTo(UPDATED_QTD_PECAS);
        assertThat(testControleAjustes.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testControleAjustes.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testControleAjustes.getReceita()).isEqualTo(UPDATED_RECEITA);
    }

    @Test
    @Transactional
    void patchNonExistingControleAjustes() throws Exception {
        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();
        controleAjustes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleAjustesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controleAjustes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControleAjustes() throws Exception {
        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();
        controleAjustes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAjustesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControleAjustes() throws Exception {
        int databaseSizeBeforeUpdate = controleAjustesRepository.findAll().size();
        controleAjustes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAjustesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleAjustes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleAjustes in the database
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControleAjustes() throws Exception {
        // Initialize the database
        controleAjustesRepository.saveAndFlush(controleAjustes);

        int databaseSizeBeforeDelete = controleAjustesRepository.findAll().size();

        // Delete the controleAjustes
        restControleAjustesMockMvc
            .perform(delete(ENTITY_API_URL_ID, controleAjustes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControleAjustes> controleAjustesList = controleAjustesRepository.findAll();
        assertThat(controleAjustesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
