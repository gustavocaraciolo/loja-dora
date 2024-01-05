package com.lojadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "primeiro_nome", nullable = false)
    private String primeiroNome;

    @NotNull
    @Column(name = "ultimo_nome", nullable = false)
    private String ultimoNome;

    @Column(name = "endereco_linha_1")
    private String enderecoLinha1;

    @Column(name = "endereco_linha_2")
    private String enderecoLinha2;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "telefone_emergencial")
    private String telefoneEmergencial;

    @Column(name = "email")
    private String email;

    @Column(name = "banco")
    private String banco;

    @Column(name = "iban")
    private String iban;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario" }, allowSetters = true)
    private Set<ControleEntregas> controleEntregases = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario" }, allowSetters = true)
    private Set<ControleFrequencia> controleFrequencias = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario" }, allowSetters = true)
    private Set<ControlePagamentoFuncionario> controlePagamentoFuncionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario" }, allowSetters = true)
    private Set<ControleAjustes> controleAjustes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimeiroNome() {
        return this.primeiroNome;
    }

    public Funcionario primeiroNome(String primeiroNome) {
        this.setPrimeiroNome(primeiroNome);
        return this;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return this.ultimoNome;
    }

    public Funcionario ultimoNome(String ultimoNome) {
        this.setUltimoNome(ultimoNome);
        return this;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getEnderecoLinha1() {
        return this.enderecoLinha1;
    }

    public Funcionario enderecoLinha1(String enderecoLinha1) {
        this.setEnderecoLinha1(enderecoLinha1);
        return this;
    }

    public void setEnderecoLinha1(String enderecoLinha1) {
        this.enderecoLinha1 = enderecoLinha1;
    }

    public String getEnderecoLinha2() {
        return this.enderecoLinha2;
    }

    public Funcionario enderecoLinha2(String enderecoLinha2) {
        this.setEnderecoLinha2(enderecoLinha2);
        return this;
    }

    public void setEnderecoLinha2(String enderecoLinha2) {
        this.enderecoLinha2 = enderecoLinha2;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public Funcionario dataInicio(LocalDate dataInicio) {
        this.setDataInicio(dataInicio);
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Funcionario telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneEmergencial() {
        return this.telefoneEmergencial;
    }

    public Funcionario telefoneEmergencial(String telefoneEmergencial) {
        this.setTelefoneEmergencial(telefoneEmergencial);
        return this;
    }

    public void setTelefoneEmergencial(String telefoneEmergencial) {
        this.telefoneEmergencial = telefoneEmergencial;
    }

    public String getEmail() {
        return this.email;
    }

    public Funcionario email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBanco() {
        return this.banco;
    }

    public Funcionario banco(String banco) {
        this.setBanco(banco);
        return this;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getIban() {
        return this.iban;
    }

    public Funcionario iban(String iban) {
        this.setIban(iban);
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Set<ControleEntregas> getControleEntregases() {
        return this.controleEntregases;
    }

    public void setControleEntregases(Set<ControleEntregas> controleEntregases) {
        if (this.controleEntregases != null) {
            this.controleEntregases.forEach(i -> i.setFuncionario(null));
        }
        if (controleEntregases != null) {
            controleEntregases.forEach(i -> i.setFuncionario(this));
        }
        this.controleEntregases = controleEntregases;
    }

    public Funcionario controleEntregases(Set<ControleEntregas> controleEntregases) {
        this.setControleEntregases(controleEntregases);
        return this;
    }

    public Funcionario addControleEntregas(ControleEntregas controleEntregas) {
        this.controleEntregases.add(controleEntregas);
        controleEntregas.setFuncionario(this);
        return this;
    }

    public Funcionario removeControleEntregas(ControleEntregas controleEntregas) {
        this.controleEntregases.remove(controleEntregas);
        controleEntregas.setFuncionario(null);
        return this;
    }

    public Set<ControleFrequencia> getControleFrequencias() {
        return this.controleFrequencias;
    }

    public void setControleFrequencias(Set<ControleFrequencia> controleFrequencias) {
        if (this.controleFrequencias != null) {
            this.controleFrequencias.forEach(i -> i.setFuncionario(null));
        }
        if (controleFrequencias != null) {
            controleFrequencias.forEach(i -> i.setFuncionario(this));
        }
        this.controleFrequencias = controleFrequencias;
    }

    public Funcionario controleFrequencias(Set<ControleFrequencia> controleFrequencias) {
        this.setControleFrequencias(controleFrequencias);
        return this;
    }

    public Funcionario addControleFrequencia(ControleFrequencia controleFrequencia) {
        this.controleFrequencias.add(controleFrequencia);
        controleFrequencia.setFuncionario(this);
        return this;
    }

    public Funcionario removeControleFrequencia(ControleFrequencia controleFrequencia) {
        this.controleFrequencias.remove(controleFrequencia);
        controleFrequencia.setFuncionario(null);
        return this;
    }

    public Set<ControlePagamentoFuncionario> getControlePagamentoFuncionarios() {
        return this.controlePagamentoFuncionarios;
    }

    public void setControlePagamentoFuncionarios(Set<ControlePagamentoFuncionario> controlePagamentoFuncionarios) {
        if (this.controlePagamentoFuncionarios != null) {
            this.controlePagamentoFuncionarios.forEach(i -> i.setFuncionario(null));
        }
        if (controlePagamentoFuncionarios != null) {
            controlePagamentoFuncionarios.forEach(i -> i.setFuncionario(this));
        }
        this.controlePagamentoFuncionarios = controlePagamentoFuncionarios;
    }

    public Funcionario controlePagamentoFuncionarios(Set<ControlePagamentoFuncionario> controlePagamentoFuncionarios) {
        this.setControlePagamentoFuncionarios(controlePagamentoFuncionarios);
        return this;
    }

    public Funcionario addControlePagamentoFuncionario(ControlePagamentoFuncionario controlePagamentoFuncionario) {
        this.controlePagamentoFuncionarios.add(controlePagamentoFuncionario);
        controlePagamentoFuncionario.setFuncionario(this);
        return this;
    }

    public Funcionario removeControlePagamentoFuncionario(ControlePagamentoFuncionario controlePagamentoFuncionario) {
        this.controlePagamentoFuncionarios.remove(controlePagamentoFuncionario);
        controlePagamentoFuncionario.setFuncionario(null);
        return this;
    }

    public Set<ControleAjustes> getControleAjustes() {
        return this.controleAjustes;
    }

    public void setControleAjustes(Set<ControleAjustes> controleAjustes) {
        if (this.controleAjustes != null) {
            this.controleAjustes.forEach(i -> i.setFuncionario(null));
        }
        if (controleAjustes != null) {
            controleAjustes.forEach(i -> i.setFuncionario(this));
        }
        this.controleAjustes = controleAjustes;
    }

    public Funcionario controleAjustes(Set<ControleAjustes> controleAjustes) {
        this.setControleAjustes(controleAjustes);
        return this;
    }

    public Funcionario addControleAjustes(ControleAjustes controleAjustes) {
        this.controleAjustes.add(controleAjustes);
        controleAjustes.setFuncionario(this);
        return this;
    }

    public Funcionario removeControleAjustes(ControleAjustes controleAjustes) {
        this.controleAjustes.remove(controleAjustes);
        controleAjustes.setFuncionario(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionario)) {
            return false;
        }
        return getId() != null && getId().equals(((Funcionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", primeiroNome='" + getPrimeiroNome() + "'" +
            ", ultimoNome='" + getUltimoNome() + "'" +
            ", enderecoLinha1='" + getEnderecoLinha1() + "'" +
            ", enderecoLinha2='" + getEnderecoLinha2() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", telefoneEmergencial='" + getTelefoneEmergencial() + "'" +
            ", email='" + getEmail() + "'" +
            ", banco='" + getBanco() + "'" +
            ", iban='" + getIban() + "'" +
            "}";
    }
}
