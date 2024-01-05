package com.lojadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lojadora.IntegrationTest;
import com.lojadora.domain.ControleDiario;
import com.lojadora.domain.enumeration.Banco;
import com.lojadora.domain.enumeration.FormasPagamento;
import com.lojadora.repository.ControleDiarioRepository;
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
 * Integration tests for the {@link ControleDiarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ControleDiarioResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE = "BBBBBBBBBB";

    private static final Float DEFAULT_VALOR_COMPRA = 1F;
    private static final Float UPDATED_VALOR_COMPRA = 2F;

    private static final Float DEFAULT_VALOR_PAGO = 1F;
    private static final Float UPDATED_VALOR_PAGO = 2F;

    private static final Float DEFAULT_SALDO_DEVEDOR = 1F;
    private static final Float UPDATED_SALDO_DEVEDOR = 2F;

    private static final Float DEFAULT_RECEBIMENTO = 1F;
    private static final Float UPDATED_RECEBIMENTO = 2F;

    private static final FormasPagamento DEFAULT_PAGAMENTO = FormasPagamento.TRANSFERENCIA;
    private static final FormasPagamento UPDATED_PAGAMENTO = FormasPagamento.DINHEIRO;

    private static final Banco DEFAULT_BANCO = Banco.SOL;
    private static final Banco UPDATED_BANCO = Banco.CAIXA_ANGOLA;

    private static final String ENTITY_API_URL = "/api/controle-diarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ControleDiarioRepository controleDiarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControleDiarioMockMvc;

    private ControleDiario controleDiario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleDiario createEntity(EntityManager em) {
        ControleDiario controleDiario = new ControleDiario()
            .data(DEFAULT_DATA)
            .cliente(DEFAULT_CLIENTE)
            .valorCompra(DEFAULT_VALOR_COMPRA)
            .valorPago(DEFAULT_VALOR_PAGO)
            .saldoDevedor(DEFAULT_SALDO_DEVEDOR)
            .recebimento(DEFAULT_RECEBIMENTO)
            .pagamento(DEFAULT_PAGAMENTO)
            .banco(DEFAULT_BANCO);
        return controleDiario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleDiario createUpdatedEntity(EntityManager em) {
        ControleDiario controleDiario = new ControleDiario()
            .data(UPDATED_DATA)
            .cliente(UPDATED_CLIENTE)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorPago(UPDATED_VALOR_PAGO)
            .saldoDevedor(UPDATED_SALDO_DEVEDOR)
            .recebimento(UPDATED_RECEBIMENTO)
            .pagamento(UPDATED_PAGAMENTO)
            .banco(UPDATED_BANCO);
        return controleDiario;
    }

    @BeforeEach
    public void initTest() {
        controleDiario = createEntity(em);
    }

    @Test
    @Transactional
    void createControleDiario() throws Exception {
        int databaseSizeBeforeCreate = controleDiarioRepository.findAll().size();
        // Create the ControleDiario
        restControleDiarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleDiario))
            )
            .andExpect(status().isCreated());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeCreate + 1);
        ControleDiario testControleDiario = controleDiarioList.get(controleDiarioList.size() - 1);
        assertThat(testControleDiario.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testControleDiario.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testControleDiario.getValorCompra()).isEqualTo(DEFAULT_VALOR_COMPRA);
        assertThat(testControleDiario.getValorPago()).isEqualTo(DEFAULT_VALOR_PAGO);
        assertThat(testControleDiario.getSaldoDevedor()).isEqualTo(DEFAULT_SALDO_DEVEDOR);
        assertThat(testControleDiario.getRecebimento()).isEqualTo(DEFAULT_RECEBIMENTO);
        assertThat(testControleDiario.getPagamento()).isEqualTo(DEFAULT_PAGAMENTO);
        assertThat(testControleDiario.getBanco()).isEqualTo(DEFAULT_BANCO);
    }

    @Test
    @Transactional
    void createControleDiarioWithExistingId() throws Exception {
        // Create the ControleDiario with an existing ID
        controleDiario.setId(1L);

        int databaseSizeBeforeCreate = controleDiarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleDiarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleDiario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllControleDiarios() throws Exception {
        // Initialize the database
        controleDiarioRepository.saveAndFlush(controleDiario);

        // Get all the controleDiarioList
        restControleDiarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleDiario.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].valorCompra").value(hasItem(DEFAULT_VALOR_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].valorPago").value(hasItem(DEFAULT_VALOR_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].saldoDevedor").value(hasItem(DEFAULT_SALDO_DEVEDOR.doubleValue())))
            .andExpect(jsonPath("$.[*].recebimento").value(hasItem(DEFAULT_RECEBIMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].pagamento").value(hasItem(DEFAULT_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO.toString())));
    }

    @Test
    @Transactional
    void getControleDiario() throws Exception {
        // Initialize the database
        controleDiarioRepository.saveAndFlush(controleDiario);

        // Get the controleDiario
        restControleDiarioMockMvc
            .perform(get(ENTITY_API_URL_ID, controleDiario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controleDiario.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.valorCompra").value(DEFAULT_VALOR_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.valorPago").value(DEFAULT_VALOR_PAGO.doubleValue()))
            .andExpect(jsonPath("$.saldoDevedor").value(DEFAULT_SALDO_DEVEDOR.doubleValue()))
            .andExpect(jsonPath("$.recebimento").value(DEFAULT_RECEBIMENTO.doubleValue()))
            .andExpect(jsonPath("$.pagamento").value(DEFAULT_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.banco").value(DEFAULT_BANCO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingControleDiario() throws Exception {
        // Get the controleDiario
        restControleDiarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingControleDiario() throws Exception {
        // Initialize the database
        controleDiarioRepository.saveAndFlush(controleDiario);

        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();

        // Update the controleDiario
        ControleDiario updatedControleDiario = controleDiarioRepository.findById(controleDiario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedControleDiario are not directly saved in db
        em.detach(updatedControleDiario);
        updatedControleDiario
            .data(UPDATED_DATA)
            .cliente(UPDATED_CLIENTE)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorPago(UPDATED_VALOR_PAGO)
            .saldoDevedor(UPDATED_SALDO_DEVEDOR)
            .recebimento(UPDATED_RECEBIMENTO)
            .pagamento(UPDATED_PAGAMENTO)
            .banco(UPDATED_BANCO);

        restControleDiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedControleDiario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedControleDiario))
            )
            .andExpect(status().isOk());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
        ControleDiario testControleDiario = controleDiarioList.get(controleDiarioList.size() - 1);
        assertThat(testControleDiario.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControleDiario.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testControleDiario.getValorCompra()).isEqualTo(UPDATED_VALOR_COMPRA);
        assertThat(testControleDiario.getValorPago()).isEqualTo(UPDATED_VALOR_PAGO);
        assertThat(testControleDiario.getSaldoDevedor()).isEqualTo(UPDATED_SALDO_DEVEDOR);
        assertThat(testControleDiario.getRecebimento()).isEqualTo(UPDATED_RECEBIMENTO);
        assertThat(testControleDiario.getPagamento()).isEqualTo(UPDATED_PAGAMENTO);
        assertThat(testControleDiario.getBanco()).isEqualTo(UPDATED_BANCO);
    }

    @Test
    @Transactional
    void putNonExistingControleDiario() throws Exception {
        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();
        controleDiario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleDiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controleDiario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleDiario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControleDiario() throws Exception {
        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();
        controleDiario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleDiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleDiario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControleDiario() throws Exception {
        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();
        controleDiario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleDiarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controleDiario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControleDiarioWithPatch() throws Exception {
        // Initialize the database
        controleDiarioRepository.saveAndFlush(controleDiario);

        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();

        // Update the controleDiario using partial update
        ControleDiario partialUpdatedControleDiario = new ControleDiario();
        partialUpdatedControleDiario.setId(controleDiario.getId());

        partialUpdatedControleDiario
            .data(UPDATED_DATA)
            .cliente(UPDATED_CLIENTE)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorPago(UPDATED_VALOR_PAGO)
            .banco(UPDATED_BANCO);

        restControleDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleDiario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleDiario))
            )
            .andExpect(status().isOk());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
        ControleDiario testControleDiario = controleDiarioList.get(controleDiarioList.size() - 1);
        assertThat(testControleDiario.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControleDiario.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testControleDiario.getValorCompra()).isEqualTo(UPDATED_VALOR_COMPRA);
        assertThat(testControleDiario.getValorPago()).isEqualTo(UPDATED_VALOR_PAGO);
        assertThat(testControleDiario.getSaldoDevedor()).isEqualTo(DEFAULT_SALDO_DEVEDOR);
        assertThat(testControleDiario.getRecebimento()).isEqualTo(DEFAULT_RECEBIMENTO);
        assertThat(testControleDiario.getPagamento()).isEqualTo(DEFAULT_PAGAMENTO);
        assertThat(testControleDiario.getBanco()).isEqualTo(UPDATED_BANCO);
    }

    @Test
    @Transactional
    void fullUpdateControleDiarioWithPatch() throws Exception {
        // Initialize the database
        controleDiarioRepository.saveAndFlush(controleDiario);

        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();

        // Update the controleDiario using partial update
        ControleDiario partialUpdatedControleDiario = new ControleDiario();
        partialUpdatedControleDiario.setId(controleDiario.getId());

        partialUpdatedControleDiario
            .data(UPDATED_DATA)
            .cliente(UPDATED_CLIENTE)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorPago(UPDATED_VALOR_PAGO)
            .saldoDevedor(UPDATED_SALDO_DEVEDOR)
            .recebimento(UPDATED_RECEBIMENTO)
            .pagamento(UPDATED_PAGAMENTO)
            .banco(UPDATED_BANCO);

        restControleDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleDiario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleDiario))
            )
            .andExpect(status().isOk());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
        ControleDiario testControleDiario = controleDiarioList.get(controleDiarioList.size() - 1);
        assertThat(testControleDiario.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControleDiario.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testControleDiario.getValorCompra()).isEqualTo(UPDATED_VALOR_COMPRA);
        assertThat(testControleDiario.getValorPago()).isEqualTo(UPDATED_VALOR_PAGO);
        assertThat(testControleDiario.getSaldoDevedor()).isEqualTo(UPDATED_SALDO_DEVEDOR);
        assertThat(testControleDiario.getRecebimento()).isEqualTo(UPDATED_RECEBIMENTO);
        assertThat(testControleDiario.getPagamento()).isEqualTo(UPDATED_PAGAMENTO);
        assertThat(testControleDiario.getBanco()).isEqualTo(UPDATED_BANCO);
    }

    @Test
    @Transactional
    void patchNonExistingControleDiario() throws Exception {
        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();
        controleDiario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controleDiario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleDiario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControleDiario() throws Exception {
        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();
        controleDiario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleDiario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControleDiario() throws Exception {
        int databaseSizeBeforeUpdate = controleDiarioRepository.findAll().size();
        controleDiario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(controleDiario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleDiario in the database
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControleDiario() throws Exception {
        // Initialize the database
        controleDiarioRepository.saveAndFlush(controleDiario);

        int databaseSizeBeforeDelete = controleDiarioRepository.findAll().size();

        // Delete the controleDiario
        restControleDiarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, controleDiario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControleDiario> controleDiarioList = controleDiarioRepository.findAll();
        assertThat(controleDiarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
