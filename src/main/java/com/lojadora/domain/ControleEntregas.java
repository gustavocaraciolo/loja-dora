package com.lojadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lojadora.domain.enumeration.Receita;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ControleEntregas.
 */
@Entity
@Table(name = "controle_entregas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ControleEntregas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "endereco")
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "receita")
    private Receita receita;

    @Column(name = "valor")
    private Float valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "controleEntregases", "controlePagamentoFuncionarios", "controleAjustes", "controleFrequencias" },
        allowSetters = true
    )
    private Funcionario funcionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ControleEntregas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public ControleEntregas data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ControleEntregas descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public ControleEntregas endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Receita getReceita() {
        return this.receita;
    }

    public ControleEntregas receita(Receita receita) {
        this.setReceita(receita);
        return this;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Float getValor() {
        return this.valor;
    }

    public ControleEntregas valor(Float valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public ControleEntregas funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleEntregas)) {
            return false;
        }
        return getId() != null && getId().equals(((ControleEntregas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleEntregas{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", receita='" + getReceita() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
