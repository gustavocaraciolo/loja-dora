package com.lojadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entidades.
 */
@Entity
@Table(name = "entidades")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entidades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private Integer telefone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entidades")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entidades" }, allowSetters = true)
    private Set<FluxoCaixa> fluxoCaixas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entidades id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Entidades nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public Entidades endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getTelefone() {
        return this.telefone;
    }

    public Entidades telefone(Integer telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Set<FluxoCaixa> getFluxoCaixas() {
        return this.fluxoCaixas;
    }

    public void setFluxoCaixas(Set<FluxoCaixa> fluxoCaixas) {
        if (this.fluxoCaixas != null) {
            this.fluxoCaixas.forEach(i -> i.setEntidades(null));
        }
        if (fluxoCaixas != null) {
            fluxoCaixas.forEach(i -> i.setEntidades(this));
        }
        this.fluxoCaixas = fluxoCaixas;
    }

    public Entidades fluxoCaixas(Set<FluxoCaixa> fluxoCaixas) {
        this.setFluxoCaixas(fluxoCaixas);
        return this;
    }

    public Entidades addFluxoCaixa(FluxoCaixa fluxoCaixa) {
        this.fluxoCaixas.add(fluxoCaixa);
        fluxoCaixa.setEntidades(this);
        return this;
    }

    public Entidades removeFluxoCaixa(FluxoCaixa fluxoCaixa) {
        this.fluxoCaixas.remove(fluxoCaixa);
        fluxoCaixa.setEntidades(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entidades)) {
            return false;
        }
        return getId() != null && getId().equals(((Entidades) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entidades{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", telefone=" + getTelefone() +
            "}";
    }
}
