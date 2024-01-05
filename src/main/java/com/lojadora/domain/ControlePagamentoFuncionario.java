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
 * A ControlePagamentoFuncionario.
 */
@Entity
@Table(name = "controle_pagamento_funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ControlePagamentoFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "salario")
    private Float salario;

    @Column(name = "beneficio")
    private Float beneficio;

    @Column(name = "comissao")
    private Float comissao;

    @Column(name = "ferias")
    private Float ferias;

    @Column(name = "adiantamento")
    private Float adiantamento;

    @Column(name = "total")
    private Float total;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_controle_pagamento_funcionario__funcionario",
        joinColumns = @JoinColumn(name = "controle_pagamento_funcionario_id"),
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

    public ControlePagamentoFuncionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public ControlePagamentoFuncionario data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getSalario() {
        return this.salario;
    }

    public ControlePagamentoFuncionario salario(Float salario) {
        this.setSalario(salario);
        return this;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    public Float getBeneficio() {
        return this.beneficio;
    }

    public ControlePagamentoFuncionario beneficio(Float beneficio) {
        this.setBeneficio(beneficio);
        return this;
    }

    public void setBeneficio(Float beneficio) {
        this.beneficio = beneficio;
    }

    public Float getComissao() {
        return this.comissao;
    }

    public ControlePagamentoFuncionario comissao(Float comissao) {
        this.setComissao(comissao);
        return this;
    }

    public void setComissao(Float comissao) {
        this.comissao = comissao;
    }

    public Float getFerias() {
        return this.ferias;
    }

    public ControlePagamentoFuncionario ferias(Float ferias) {
        this.setFerias(ferias);
        return this;
    }

    public void setFerias(Float ferias) {
        this.ferias = ferias;
    }

    public Float getAdiantamento() {
        return this.adiantamento;
    }

    public ControlePagamentoFuncionario adiantamento(Float adiantamento) {
        this.setAdiantamento(adiantamento);
        return this;
    }

    public void setAdiantamento(Float adiantamento) {
        this.adiantamento = adiantamento;
    }

    public Float getTotal() {
        return this.total;
    }

    public ControlePagamentoFuncionario total(Float total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public ControlePagamentoFuncionario funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public ControlePagamentoFuncionario addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        return this;
    }

    public ControlePagamentoFuncionario removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControlePagamentoFuncionario)) {
            return false;
        }
        return getId() != null && getId().equals(((ControlePagamentoFuncionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControlePagamentoFuncionario{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", salario=" + getSalario() +
            ", beneficio=" + getBeneficio() +
            ", comissao=" + getComissao() +
            ", ferias=" + getFerias() +
            ", adiantamento=" + getAdiantamento() +
            ", total=" + getTotal() +
            "}";
    }
}
