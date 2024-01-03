package com.lojadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ControleFrequencia.
 */
@Entity
@Table(name = "controle_frequencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ControleFrequencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_trabalho")
    private LocalDate dataTrabalho;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_controle_frequencia__funcionario",
        joinColumns = @JoinColumn(name = "controle_frequencia_id"),
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

    public ControleFrequencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataTrabalho() {
        return this.dataTrabalho;
    }

    public ControleFrequencia dataTrabalho(LocalDate dataTrabalho) {
        this.setDataTrabalho(dataTrabalho);
        return this;
    }

    public void setDataTrabalho(LocalDate dataTrabalho) {
        this.dataTrabalho = dataTrabalho;
    }

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public ControleFrequencia funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public ControleFrequencia addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        return this;
    }

    public ControleFrequencia removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleFrequencia)) {
            return false;
        }
        return getId() != null && getId().equals(((ControleFrequencia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleFrequencia{" +
            "id=" + getId() +
            ", dataTrabalho='" + getDataTrabalho() + "'" +
            "}";
    }
}
