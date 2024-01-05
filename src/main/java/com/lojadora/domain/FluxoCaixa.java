package com.lojadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lojadora.domain.enumeration.Banco;
import com.lojadora.domain.enumeration.EntradaSaida;
import com.lojadora.domain.enumeration.FixoVariavel;
import com.lojadora.domain.enumeration.Pais;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FluxoCaixa.
 */
@Entity
@Table(name = "fluxo_caixa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FluxoCaixa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "saldo")
    private Float saldo;

    @Enumerated(EnumType.STRING)
    @Column(name = "banco")
    private Banco banco;

    @Column(name = "valor")
    private Float valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "fixo_variavel")
    private FixoVariavel fixoVariavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "entrada_saida")
    private EntradaSaida entradaSaida;

    @Enumerated(EnumType.STRING)
    @Column(name = "pais")
    private Pais pais;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_fluxo_caixa__entidades",
        joinColumns = @JoinColumn(name = "fluxo_caixa_id"),
        inverseJoinColumns = @JoinColumn(name = "entidades_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fluxoCaixas" }, allowSetters = true)
    private Set<Entidades> entidades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FluxoCaixa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public FluxoCaixa data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getSaldo() {
        return this.saldo;
    }

    public FluxoCaixa saldo(Float saldo) {
        this.setSaldo(saldo);
        return this;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public FluxoCaixa banco(Banco banco) {
        this.setBanco(banco);
        return this;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Float getValor() {
        return this.valor;
    }

    public FluxoCaixa valor(Float valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public FixoVariavel getFixoVariavel() {
        return this.fixoVariavel;
    }

    public FluxoCaixa fixoVariavel(FixoVariavel fixoVariavel) {
        this.setFixoVariavel(fixoVariavel);
        return this;
    }

    public void setFixoVariavel(FixoVariavel fixoVariavel) {
        this.fixoVariavel = fixoVariavel;
    }

    public EntradaSaida getEntradaSaida() {
        return this.entradaSaida;
    }

    public FluxoCaixa entradaSaida(EntradaSaida entradaSaida) {
        this.setEntradaSaida(entradaSaida);
        return this;
    }

    public void setEntradaSaida(EntradaSaida entradaSaida) {
        this.entradaSaida = entradaSaida;
    }

    public Pais getPais() {
        return this.pais;
    }

    public FluxoCaixa pais(Pais pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Set<Entidades> getEntidades() {
        return this.entidades;
    }

    public void setEntidades(Set<Entidades> entidades) {
        this.entidades = entidades;
    }

    public FluxoCaixa entidades(Set<Entidades> entidades) {
        this.setEntidades(entidades);
        return this;
    }

    public FluxoCaixa addEntidades(Entidades entidades) {
        this.entidades.add(entidades);
        return this;
    }

    public FluxoCaixa removeEntidades(Entidades entidades) {
        this.entidades.remove(entidades);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FluxoCaixa)) {
            return false;
        }
        return getId() != null && getId().equals(((FluxoCaixa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FluxoCaixa{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", saldo=" + getSaldo() +
            ", banco='" + getBanco() + "'" +
            ", valor=" + getValor() +
            ", fixoVariavel='" + getFixoVariavel() + "'" +
            ", entradaSaida='" + getEntradaSaida() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
