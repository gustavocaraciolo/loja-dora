package com.lojadora.domain;

import com.lojadora.domain.enumeration.Banco;
import com.lojadora.domain.enumeration.FormasPagamento;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ControleDiario.
 */
@Entity
@Table(name = "controle_diario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ControleDiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "valor_compra")
    private Float valorCompra;

    @Column(name = "valor_pago")
    private Float valorPago;

    @Column(name = "saldo_devedor")
    private Float saldoDevedor;

    @Column(name = "recebimento")
    private Float recebimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento")
    private FormasPagamento pagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "banco")
    private Banco banco;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ControleDiario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public ControleDiario data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCliente() {
        return this.cliente;
    }

    public ControleDiario cliente(String cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Float getValorCompra() {
        return this.valorCompra;
    }

    public ControleDiario valorCompra(Float valorCompra) {
        this.setValorCompra(valorCompra);
        return this;
    }

    public void setValorCompra(Float valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Float getValorPago() {
        return this.valorPago;
    }

    public ControleDiario valorPago(Float valorPago) {
        this.setValorPago(valorPago);
        return this;
    }

    public void setValorPago(Float valorPago) {
        this.valorPago = valorPago;
    }

    public Float getSaldoDevedor() {
        return this.saldoDevedor;
    }

    public ControleDiario saldoDevedor(Float saldoDevedor) {
        this.setSaldoDevedor(saldoDevedor);
        return this;
    }

    public void setSaldoDevedor(Float saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    public Float getRecebimento() {
        return this.recebimento;
    }

    public ControleDiario recebimento(Float recebimento) {
        this.setRecebimento(recebimento);
        return this;
    }

    public void setRecebimento(Float recebimento) {
        this.recebimento = recebimento;
    }

    public FormasPagamento getPagamento() {
        return this.pagamento;
    }

    public ControleDiario pagamento(FormasPagamento pagamento) {
        this.setPagamento(pagamento);
        return this;
    }

    public void setPagamento(FormasPagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public ControleDiario banco(Banco banco) {
        this.setBanco(banco);
        return this;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleDiario)) {
            return false;
        }
        return getId() != null && getId().equals(((ControleDiario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleDiario{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", cliente='" + getCliente() + "'" +
            ", valorCompra=" + getValorCompra() +
            ", valorPago=" + getValorPago() +
            ", saldoDevedor=" + getSaldoDevedor() +
            ", recebimento=" + getRecebimento() +
            ", pagamento='" + getPagamento() + "'" +
            ", banco='" + getBanco() + "'" +
            "}";
    }
}
