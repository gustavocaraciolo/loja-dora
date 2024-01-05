package com.lojadora.domain;

import static com.lojadora.domain.ControleAjustesTestSamples.*;
import static com.lojadora.domain.ControleEntregasTestSamples.*;
import static com.lojadora.domain.ControleFrequenciaTestSamples.*;
import static com.lojadora.domain.ControlePagamentoFuncionarioTestSamples.*;
import static com.lojadora.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionario.class);
        Funcionario funcionario1 = getFuncionarioSample1();
        Funcionario funcionario2 = new Funcionario();
        assertThat(funcionario1).isNotEqualTo(funcionario2);

        funcionario2.setId(funcionario1.getId());
        assertThat(funcionario1).isEqualTo(funcionario2);

        funcionario2 = getFuncionarioSample2();
        assertThat(funcionario1).isNotEqualTo(funcionario2);
    }

    @Test
    void controleEntregasTest() throws Exception {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        ControleEntregas controleEntregasBack = getControleEntregasRandomSampleGenerator();

        funcionario.addControleEntregas(controleEntregasBack);
        assertThat(funcionario.getControleEntregases()).containsOnly(controleEntregasBack);
        assertThat(controleEntregasBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.removeControleEntregas(controleEntregasBack);
        assertThat(funcionario.getControleEntregases()).doesNotContain(controleEntregasBack);
        assertThat(controleEntregasBack.getFuncionario()).isNull();

        funcionario.controleEntregases(new HashSet<>(Set.of(controleEntregasBack)));
        assertThat(funcionario.getControleEntregases()).containsOnly(controleEntregasBack);
        assertThat(controleEntregasBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.setControleEntregases(new HashSet<>());
        assertThat(funcionario.getControleEntregases()).doesNotContain(controleEntregasBack);
        assertThat(controleEntregasBack.getFuncionario()).isNull();
    }

    @Test
    void controlePagamentoFuncionarioTest() throws Exception {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        ControlePagamentoFuncionario controlePagamentoFuncionarioBack = getControlePagamentoFuncionarioRandomSampleGenerator();

        funcionario.addControlePagamentoFuncionario(controlePagamentoFuncionarioBack);
        assertThat(funcionario.getControlePagamentoFuncionarios()).containsOnly(controlePagamentoFuncionarioBack);
        assertThat(controlePagamentoFuncionarioBack.getFuncionarios()).containsOnly(funcionario);

        funcionario.removeControlePagamentoFuncionario(controlePagamentoFuncionarioBack);
        assertThat(funcionario.getControlePagamentoFuncionarios()).doesNotContain(controlePagamentoFuncionarioBack);
        assertThat(controlePagamentoFuncionarioBack.getFuncionarios()).doesNotContain(funcionario);

        funcionario.controlePagamentoFuncionarios(new HashSet<>(Set.of(controlePagamentoFuncionarioBack)));
        assertThat(funcionario.getControlePagamentoFuncionarios()).containsOnly(controlePagamentoFuncionarioBack);
        assertThat(controlePagamentoFuncionarioBack.getFuncionarios()).containsOnly(funcionario);

        funcionario.setControlePagamentoFuncionarios(new HashSet<>());
        assertThat(funcionario.getControlePagamentoFuncionarios()).doesNotContain(controlePagamentoFuncionarioBack);
        assertThat(controlePagamentoFuncionarioBack.getFuncionarios()).doesNotContain(funcionario);
    }

    @Test
    void controleAjustesTest() throws Exception {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        ControleAjustes controleAjustesBack = getControleAjustesRandomSampleGenerator();

        funcionario.addControleAjustes(controleAjustesBack);
        assertThat(funcionario.getControleAjustes()).containsOnly(controleAjustesBack);
        assertThat(controleAjustesBack.getFuncionarios()).containsOnly(funcionario);

        funcionario.removeControleAjustes(controleAjustesBack);
        assertThat(funcionario.getControleAjustes()).doesNotContain(controleAjustesBack);
        assertThat(controleAjustesBack.getFuncionarios()).doesNotContain(funcionario);

        funcionario.controleAjustes(new HashSet<>(Set.of(controleAjustesBack)));
        assertThat(funcionario.getControleAjustes()).containsOnly(controleAjustesBack);
        assertThat(controleAjustesBack.getFuncionarios()).containsOnly(funcionario);

        funcionario.setControleAjustes(new HashSet<>());
        assertThat(funcionario.getControleAjustes()).doesNotContain(controleAjustesBack);
        assertThat(controleAjustesBack.getFuncionarios()).doesNotContain(funcionario);
    }

    @Test
    void controleFrequenciaTest() throws Exception {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        ControleFrequencia controleFrequenciaBack = getControleFrequenciaRandomSampleGenerator();

        funcionario.addControleFrequencia(controleFrequenciaBack);
        assertThat(funcionario.getControleFrequencias()).containsOnly(controleFrequenciaBack);
        assertThat(controleFrequenciaBack.getFuncionarios()).containsOnly(funcionario);

        funcionario.removeControleFrequencia(controleFrequenciaBack);
        assertThat(funcionario.getControleFrequencias()).doesNotContain(controleFrequenciaBack);
        assertThat(controleFrequenciaBack.getFuncionarios()).doesNotContain(funcionario);

        funcionario.controleFrequencias(new HashSet<>(Set.of(controleFrequenciaBack)));
        assertThat(funcionario.getControleFrequencias()).containsOnly(controleFrequenciaBack);
        assertThat(controleFrequenciaBack.getFuncionarios()).containsOnly(funcionario);

        funcionario.setControleFrequencias(new HashSet<>());
        assertThat(funcionario.getControleFrequencias()).doesNotContain(controleFrequenciaBack);
        assertThat(controleFrequenciaBack.getFuncionarios()).doesNotContain(funcionario);
    }
}
