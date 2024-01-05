package com.lojadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lojadora.domain.enumeration.Receita;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ControleAjustes.
 */
@Entity
@Table(name = "controle_ajustes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ControleAjustes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Column(name = "data_recebimento")
    private LocalDate dataRecebimento;

    @Column(name = "qtd_pecas")
    private Integer qtdPecas;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private Float valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "receita")
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "controleEntregases", "controleFrequencias", "controlePagamentoFuncionarios", "controleAjustes" },
        allowSetters = true
    )
    private Funcionario funcionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ControleAjustes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEntrega() {
        return this.dataEntrega;
    }

    public ControleAjustes dataEntrega(LocalDate dataEntrega) {
        this.setDataEntrega(dataEntrega);
        return this;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public LocalDate getDataRecebimento() {
        return this.dataRecebimento;
    }

    public ControleAjustes dataRecebimento(LocalDate dataRecebimento) {
        this.setDataRecebimento(dataRecebimento);
        return this;
    }

    public void setDataRecebimento(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Integer getQtdPecas() {
        return this.qtdPecas;
    }

    public ControleAjustes qtdPecas(Integer qtdPecas) {
        this.setQtdPecas(qtdPecas);
        return this;
    }

    public void setQtdPecas(Integer qtdPecas) {
        this.qtdPecas = qtdPecas;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ControleAjustes descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValor() {
        return this.valor;
    }

    public ControleAjustes valor(Float valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Receita getReceita() {
        return this.receita;
    }

    public ControleAjustes receita(Receita receita) {
        this.setReceita(receita);
        return this;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public ControleAjustes funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleAjustes)) {
            return false;
        }
        return getId() != null && getId().equals(((ControleAjustes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleAjustes{" +
            "id=" + getId() +
            ", dataEntrega='" + getDataEntrega() + "'" +
            ", dataRecebimento='" + getDataRecebimento() + "'" +
            ", qtdPecas=" + getQtdPecas() +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            ", receita='" + getReceita() + "'" +
            "}";
    }
}
