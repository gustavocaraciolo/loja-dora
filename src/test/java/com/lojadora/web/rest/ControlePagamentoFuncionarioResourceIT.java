package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.ControlePagamentoFuncionario;
import com.lojadora.repository.ControlePagamentoFuncionarioRepository;
import com.lojadora.service.ControlePagamentoFuncionarioService;
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
 * Integration tests for the {@link ControlePagamentoFuncionarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ControlePagamentoFuncionarioResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_SALARIO = 1F;
    private static final Float UPDATED_SALARIO = 2F;

    private static final Float DEFAULT_BENEFICIO = 1F;
    private static final Float UPDATED_BENEFICIO = 2F;

    private static final Float DEFAULT_COMISSAO = 1F;
    private static final Float UPDATED_COMISSAO = 2F;

    private static final Float DEFAULT_FERIAS = 1F;
    private static final Float UPDATED_FERIAS = 2F;

    private static final Float DEFAULT_ADIANTAMENTO = 1F;
    private static final Float UPDATED_ADIANTAMENTO = 2F;

    private static final Float DEFAULT_TOTAL = 1F;
    private static final Float UPDATED_TOTAL = 2F;

    private static final String ENTITY_API_URL = "/api/controle-pagamento-funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ControlePagamentoFuncionarioRepository controlePagamentoFuncionarioRepository;

    @Mock
    private ControlePagamentoFuncionarioRepository controlePagamentoFuncionarioRepositoryMock;

    @Mock
    private ControlePagamentoFuncionarioService controlePagamentoFuncionarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControlePagamentoFuncionarioMockMvc;

    private ControlePagamentoFuncionario controlePagamentoFuncionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControlePagamentoFuncionario createEntity(EntityManager em) {
        ControlePagamentoFuncionario controlePagamentoFuncionario = new ControlePagamentoFuncionario()
            .data(DEFAULT_DATA)
            .salario(DEFAULT_SALARIO)
            .beneficio(DEFAULT_BENEFICIO)
            .comissao(DEFAULT_COMISSAO)
            .ferias(DEFAULT_FERIAS)
            .adiantamento(DEFAULT_ADIANTAMENTO)
            .total(DEFAULT_TOTAL);
        return controlePagamentoFuncionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControlePagamentoFuncionario createUpdatedEntity(EntityManager em) {
        ControlePagamentoFuncionario controlePagamentoFuncionario = new ControlePagamentoFuncionario()
            .data(UPDATED_DATA)
            .salario(UPDATED_SALARIO)
            .beneficio(UPDATED_BENEFICIO)
            .comissao(UPDATED_COMISSAO)
            .ferias(UPDATED_FERIAS)
            .adiantamento(UPDATED_ADIANTAMENTO)
            .total(UPDATED_TOTAL);
        return controlePagamentoFuncionario;
    }

    @BeforeEach
    public void initTest() {
        controlePagamentoFuncionario = createEntity(em);
    }

    @Test
    @Transactional
    void createControlePagamentoFuncionario() throws Exception {
        int databaseSizeBeforeCreate = controlePagamentoFuncionarioRepository.findAll().size();
        // Create the ControlePagamentoFuncionario
        restControlePagamentoFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isCreated());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeCreate + 1);
        ControlePagamentoFuncionario testControlePagamentoFuncionario = controlePagamentoFuncionarioList.get(
            controlePagamentoFuncionarioList.size() - 1
        );
        assertThat(testControlePagamentoFuncionario.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testControlePagamentoFuncionario.getSalario()).isEqualTo(DEFAULT_SALARIO);
        assertThat(testControlePagamentoFuncionario.getBeneficio()).isEqualTo(DEFAULT_BENEFICIO);
        assertThat(testControlePagamentoFuncionario.getComissao()).isEqualTo(DEFAULT_COMISSAO);
        assertThat(testControlePagamentoFuncionario.getFerias()).isEqualTo(DEFAULT_FERIAS);
        assertThat(testControlePagamentoFuncionario.getAdiantamento()).isEqualTo(DEFAULT_ADIANTAMENTO);
        assertThat(testControlePagamentoFuncionario.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    void createControlePagamentoFuncionarioWithExistingId() throws Exception {
        // Create the ControlePagamentoFuncionario with an existing ID
        controlePagamentoFuncionario.setId(1L);

        int databaseSizeBeforeCreate = controlePagamentoFuncionarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlePagamentoFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllControlePagamentoFuncionarios() throws Exception {
        // Initialize the database
        controlePagamentoFuncionarioRepository.saveAndFlush(controlePagamentoFuncionario);

        // Get all the controlePagamentoFuncionarioList
        restControlePagamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controlePagamentoFuncionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].beneficio").value(hasItem(DEFAULT_BENEFICIO.doubleValue())))
            .andExpect(jsonPath("$.[*].comissao").value(hasItem(DEFAULT_COMISSAO.doubleValue())))
            .andExpect(jsonPath("$.[*].ferias").value(hasItem(DEFAULT_FERIAS.doubleValue())))
            .andExpect(jsonPath("$.[*].adiantamento").value(hasItem(DEFAULT_ADIANTAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControlePagamentoFuncionariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(controlePagamentoFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControlePagamentoFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(controlePagamentoFuncionarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControlePagamentoFuncionariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(controlePagamentoFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControlePagamentoFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(controlePagamentoFuncionarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getControlePagamentoFuncionario() throws Exception {
        // Initialize the database
        controlePagamentoFuncionarioRepository.saveAndFlush(controlePagamentoFuncionario);

        // Get the controlePagamentoFuncionario
        restControlePagamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, controlePagamentoFuncionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controlePagamentoFuncionario.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.salario").value(DEFAULT_SALARIO.doubleValue()))
            .andExpect(jsonPath("$.beneficio").value(DEFAULT_BENEFICIO.doubleValue()))
            .andExpect(jsonPath("$.comissao").value(DEFAULT_COMISSAO.doubleValue()))
            .andExpect(jsonPath("$.ferias").value(DEFAULT_FERIAS.doubleValue()))
            .andExpect(jsonPath("$.adiantamento").value(DEFAULT_ADIANTAMENTO.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingControlePagamentoFuncionario() throws Exception {
        // Get the controlePagamentoFuncionario
        restControlePagamentoFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingControlePagamentoFuncionario() throws Exception {
        // Initialize the database
        controlePagamentoFuncionarioRepository.saveAndFlush(controlePagamentoFuncionario);

        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();

        // Update the controlePagamentoFuncionario
        ControlePagamentoFuncionario updatedControlePagamentoFuncionario = controlePagamentoFuncionarioRepository
            .findById(controlePagamentoFuncionario.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedControlePagamentoFuncionario are not directly saved in db
        em.detach(updatedControlePagamentoFuncionario);
        updatedControlePagamentoFuncionario
            .data(UPDATED_DATA)
            .salario(UPDATED_SALARIO)
            .beneficio(UPDATED_BENEFICIO)
            .comissao(UPDATED_COMISSAO)
            .ferias(UPDATED_FERIAS)
            .adiantamento(UPDATED_ADIANTAMENTO)
            .total(UPDATED_TOTAL);

        restControlePagamentoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedControlePagamentoFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedControlePagamentoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
        ControlePagamentoFuncionario testControlePagamentoFuncionario = controlePagamentoFuncionarioList.get(
            controlePagamentoFuncionarioList.size() - 1
        );
        assertThat(testControlePagamentoFuncionario.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControlePagamentoFuncionario.getSalario()).isEqualTo(UPDATED_SALARIO);
        assertThat(testControlePagamentoFuncionario.getBeneficio()).isEqualTo(UPDATED_BENEFICIO);
        assertThat(testControlePagamentoFuncionario.getComissao()).isEqualTo(UPDATED_COMISSAO);
        assertThat(testControlePagamentoFuncionario.getFerias()).isEqualTo(UPDATED_FERIAS);
        assertThat(testControlePagamentoFuncionario.getAdiantamento()).isEqualTo(UPDATED_ADIANTAMENTO);
        assertThat(testControlePagamentoFuncionario.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingControlePagamentoFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();
        controlePagamentoFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlePagamentoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controlePagamentoFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControlePagamentoFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();
        controlePagamentoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlePagamentoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControlePagamentoFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();
        controlePagamentoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlePagamentoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControlePagamentoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        controlePagamentoFuncionarioRepository.saveAndFlush(controlePagamentoFuncionario);

        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();

        // Update the controlePagamentoFuncionario using partial update
        ControlePagamentoFuncionario partialUpdatedControlePagamentoFuncionario = new ControlePagamentoFuncionario();
        partialUpdatedControlePagamentoFuncionario.setId(controlePagamentoFuncionario.getId());

        partialUpdatedControlePagamentoFuncionario.data(UPDATED_DATA).ferias(UPDATED_FERIAS);

        restControlePagamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControlePagamentoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControlePagamentoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
        ControlePagamentoFuncionario testControlePagamentoFuncionario = controlePagamentoFuncionarioList.get(
            controlePagamentoFuncionarioList.size() - 1
        );
        assertThat(testControlePagamentoFuncionario.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControlePagamentoFuncionario.getSalario()).isEqualTo(DEFAULT_SALARIO);
        assertThat(testControlePagamentoFuncionario.getBeneficio()).isEqualTo(DEFAULT_BENEFICIO);
        assertThat(testControlePagamentoFuncionario.getComissao()).isEqualTo(DEFAULT_COMISSAO);
        assertThat(testControlePagamentoFuncionario.getFerias()).isEqualTo(UPDATED_FERIAS);
        assertThat(testControlePagamentoFuncionario.getAdiantamento()).isEqualTo(DEFAULT_ADIANTAMENTO);
        assertThat(testControlePagamentoFuncionario.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateControlePagamentoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        controlePagamentoFuncionarioRepository.saveAndFlush(controlePagamentoFuncionario);

        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();

        // Update the controlePagamentoFuncionario using partial update
        ControlePagamentoFuncionario partialUpdatedControlePagamentoFuncionario = new ControlePagamentoFuncionario();
        partialUpdatedControlePagamentoFuncionario.setId(controlePagamentoFuncionario.getId());

        partialUpdatedControlePagamentoFuncionario
            .data(UPDATED_DATA)
            .salario(UPDATED_SALARIO)
            .beneficio(UPDATED_BENEFICIO)
            .comissao(UPDATED_COMISSAO)
            .ferias(UPDATED_FERIAS)
            .adiantamento(UPDATED_ADIANTAMENTO)
            .total(UPDATED_TOTAL);

        restControlePagamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControlePagamentoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControlePagamentoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
        ControlePagamentoFuncionario testControlePagamentoFuncionario = controlePagamentoFuncionarioList.get(
            controlePagamentoFuncionarioList.size() - 1
        );
        assertThat(testControlePagamentoFuncionario.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControlePagamentoFuncionario.getSalario()).isEqualTo(UPDATED_SALARIO);
        assertThat(testControlePagamentoFuncionario.getBeneficio()).isEqualTo(UPDATED_BENEFICIO);
        assertThat(testControlePagamentoFuncionario.getComissao()).isEqualTo(UPDATED_COMISSAO);
        assertThat(testControlePagamentoFuncionario.getFerias()).isEqualTo(UPDATED_FERIAS);
        assertThat(testControlePagamentoFuncionario.getAdiantamento()).isEqualTo(UPDATED_ADIANTAMENTO);
        assertThat(testControlePagamentoFuncionario.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingControlePagamentoFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();
        controlePagamentoFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlePagamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controlePagamentoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControlePagamentoFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();
        controlePagamentoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlePagamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControlePagamentoFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = controlePagamentoFuncionarioRepository.findAll().size();
        controlePagamentoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlePagamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controlePagamentoFuncionario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControlePagamentoFuncionario in the database
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControlePagamentoFuncionario() throws Exception {
        // Initialize the database
        controlePagamentoFuncionarioRepository.saveAndFlush(controlePagamentoFuncionario);

        int databaseSizeBeforeDelete = controlePagamentoFuncionarioRepository.findAll().size();

        // Delete the controlePagamentoFuncionario
        restControlePagamentoFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, controlePagamentoFuncionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControlePagamentoFuncionario> controlePagamentoFuncionarioList = controlePagamentoFuncionarioRepository.findAll();
        assertThat(controlePagamentoFuncionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
