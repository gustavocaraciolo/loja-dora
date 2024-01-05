package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.ControleEntregas;
import com.lojadora.domain.enumeration.Receita;
import com.lojadora.repository.ControleEntregasRepository;
import com.lojadora.service.ControleEntregasService;
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
 * Integration tests for the {@link ControleEntregasResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ControleEntregasResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Receita DEFAULT_RECEITA = Receita.DORA;
    private static final Receita UPDATED_RECEITA = Receita.CLIENTE;

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final String ENTITY_API_URL = "/api/controle-entregases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ControleEntregasRepository controleEntregasRepository;

    @Mock
    private ControleEntregasRepository controleEntregasRepositoryMock;

    @Mock
    private ControleEntregasService controleEntregasServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControleEntregasMockMvc;

    private ControleEntregas controleEntregas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleEntregas createEntity(EntityManager em) {
        ControleEntregas controleEntregas = new ControleEntregas()
            .data(DEFAULT_DATA)
            .descricao(DEFAULT_DESCRICAO)
            .address(DEFAULT_ADDRESS)
            .receita(DEFAULT_RECEITA)
            .valor(DEFAULT_VALOR);
        return controleEntregas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleEntregas createUpdatedEntity(EntityManager em) {
        ControleEntregas controleEntregas = new ControleEntregas()
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .address(UPDATED_ADDRESS)
            .receita(UPDATED_RECEITA)
            .valor(UPDATED_VALOR);
        return controleEntregas;
    }

    @BeforeEach
    public void initTest() {
        controleEntregas = createEntity(em);
    }

    @Test
    @Transactional
    void createControleEntregas() throws Exception {
        int databaseSizeBeforeCreate = controleEntregasRepository.findAll().size();
        // Create the ControleEntregas
        restControleEntregasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isCreated());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeCreate + 1);
        ControleEntregas testControleEntregas = controleEntregasList.get(controleEntregasList.size() - 1);
        assertThat(testControleEntregas.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testControleEntregas.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testControleEntregas.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testControleEntregas.getReceita()).isEqualTo(DEFAULT_RECEITA);
        assertThat(testControleEntregas.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createControleEntregasWithExistingId() throws Exception {
        // Create the ControleEntregas with an existing ID
        controleEntregas.setId(1L);

        int databaseSizeBeforeCreate = controleEntregasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleEntregasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllControleEntregases() throws Exception {
        // Initialize the database
        controleEntregasRepository.saveAndFlush(controleEntregas);

        // Get all the controleEntregasList
        restControleEntregasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleEntregas.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].receita").value(hasItem(DEFAULT_RECEITA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControleEntregasesWithEagerRelationshipsIsEnabled() throws Exception {
        when(controleEntregasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControleEntregasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(controleEntregasServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControleEntregasesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(controleEntregasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControleEntregasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(controleEntregasRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getControleEntregas() throws Exception {
        // Initialize the database
        controleEntregasRepository.saveAndFlush(controleEntregas);

        // Get the controleEntregas
        restControleEntregasMockMvc
            .perform(get(ENTITY_API_URL_ID, controleEntregas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controleEntregas.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.receita").value(DEFAULT_RECEITA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingControleEntregas() throws Exception {
        // Get the controleEntregas
        restControleEntregasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingControleEntregas() throws Exception {
        // Initialize the database
        controleEntregasRepository.saveAndFlush(controleEntregas);

        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();

        // Update the controleEntregas
        ControleEntregas updatedControleEntregas = controleEntregasRepository.findById(controleEntregas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedControleEntregas are not directly saved in db
        em.detach(updatedControleEntregas);
        updatedControleEntregas
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .address(UPDATED_ADDRESS)
            .receita(UPDATED_RECEITA)
            .valor(UPDATED_VALOR);

        restControleEntregasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedControleEntregas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedControleEntregas))
            )
            .andExpect(status().isOk());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
        ControleEntregas testControleEntregas = controleEntregasList.get(controleEntregasList.size() - 1);
        assertThat(testControleEntregas.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControleEntregas.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testControleEntregas.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testControleEntregas.getReceita()).isEqualTo(UPDATED_RECEITA);
        assertThat(testControleEntregas.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingControleEntregas() throws Exception {
        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();
        controleEntregas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleEntregasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controleEntregas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControleEntregas() throws Exception {
        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();
        controleEntregas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleEntregasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControleEntregas() throws Exception {
        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();
        controleEntregas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleEntregasMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControleEntregasWithPatch() throws Exception {
        // Initialize the database
        controleEntregasRepository.saveAndFlush(controleEntregas);

        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();

        // Update the controleEntregas using partial update
        ControleEntregas partialUpdatedControleEntregas = new ControleEntregas();
        partialUpdatedControleEntregas.setId(controleEntregas.getId());

        partialUpdatedControleEntregas.address(UPDATED_ADDRESS).receita(UPDATED_RECEITA).valor(UPDATED_VALOR);

        restControleEntregasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleEntregas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleEntregas))
            )
            .andExpect(status().isOk());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
        ControleEntregas testControleEntregas = controleEntregasList.get(controleEntregasList.size() - 1);
        assertThat(testControleEntregas.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testControleEntregas.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testControleEntregas.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testControleEntregas.getReceita()).isEqualTo(UPDATED_RECEITA);
        assertThat(testControleEntregas.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateControleEntregasWithPatch() throws Exception {
        // Initialize the database
        controleEntregasRepository.saveAndFlush(controleEntregas);

        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();

        // Update the controleEntregas using partial update
        ControleEntregas partialUpdatedControleEntregas = new ControleEntregas();
        partialUpdatedControleEntregas.setId(controleEntregas.getId());

        partialUpdatedControleEntregas
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .address(UPDATED_ADDRESS)
            .receita(UPDATED_RECEITA)
            .valor(UPDATED_VALOR);

        restControleEntregasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleEntregas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleEntregas))
            )
            .andExpect(status().isOk());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
        ControleEntregas testControleEntregas = controleEntregasList.get(controleEntregasList.size() - 1);
        assertThat(testControleEntregas.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControleEntregas.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testControleEntregas.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testControleEntregas.getReceita()).isEqualTo(UPDATED_RECEITA);
        assertThat(testControleEntregas.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingControleEntregas() throws Exception {
        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();
        controleEntregas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleEntregasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controleEntregas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControleEntregas() throws Exception {
        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();
        controleEntregas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleEntregasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControleEntregas() throws Exception {
        int databaseSizeBeforeUpdate = controleEntregasRepository.findAll().size();
        controleEntregas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleEntregasMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleEntregas))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleEntregas in the database
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControleEntregas() throws Exception {
        // Initialize the database
        controleEntregasRepository.saveAndFlush(controleEntregas);

        int databaseSizeBeforeDelete = controleEntregasRepository.findAll().size();

        // Delete the controleEntregas
        restControleEntregasMockMvc
            .perform(delete(ENTITY_API_URL_ID, controleEntregas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControleEntregas> controleEntregasList = controleEntregasRepository.findAll();
        assertThat(controleEntregasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
