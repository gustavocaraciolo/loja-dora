package com.lojadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lojadora.domain.enumeration.Receita;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "receita")
    private Receita receita;

    @Column(name = "valor")
    private Float valor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_controle_entregas__funcionario",
        joinColumns = @JoinColumn(name = "controle_entregas_id"),
        inverseJoinColumns = @JoinColumn(name = "funcionario_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "controlePagamentoFuncionarios", "controleAjustes", "controleEntregases", "controleFrequencias" },
        allowSetters = true
    )
    private Set<Funcionario> funcionarios = new HashSet<>();

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

    public String getAddress() {
        return this.address;
    }

    public ControleEntregas address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public ControleEntregas funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public ControleEntregas addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        return this;
    }

    public ControleEntregas removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
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
            ", address='" + getAddress() + "'" +
            ", receita='" + getReceita() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
