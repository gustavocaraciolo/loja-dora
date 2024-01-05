package com.lojadora.domain;

import static com.lojadora.domain.ControlePagamentoFuncionarioTestSamples.*;
import static com.lojadora.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ControlePagamentoFuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlePagamentoFuncionario.class);
        ControlePagamentoFuncionario controlePagamentoFuncionario1 = getControlePagamentoFuncionarioSample1();
        ControlePagamentoFuncionario controlePagamentoFuncionario2 = new ControlePagamentoFuncionario();
        assertThat(controlePagamentoFuncionario1).isNotEqualTo(controlePagamentoFuncionario2);

        controlePagamentoFuncionario2.setId(controlePagamentoFuncionario1.getId());
        assertThat(controlePagamentoFuncionario1).isEqualTo(controlePagamentoFuncionario2);

        controlePagamentoFuncionario2 = getControlePagamentoFuncionarioSample2();
        assertThat(controlePagamentoFuncionario1).isNotEqualTo(controlePagamentoFuncionario2);
    }

    @Test
    void funcionarioTest() throws Exception {
        ControlePagamentoFuncionario controlePagamentoFuncionario = getControlePagamentoFuncionarioRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        controlePagamentoFuncionario.addFuncionario(funcionarioBack);
        assertThat(controlePagamentoFuncionario.getFuncionarios()).containsOnly(funcionarioBack);

        controlePagamentoFuncionario.removeFuncionario(funcionarioBack);
        assertThat(controlePagamentoFuncionario.getFuncionarios()).doesNotContain(funcionarioBack);

        controlePagamentoFuncionario.funcionarios(new HashSet<>(Set.of(funcionarioBack)));
        assertThat(controlePagamentoFuncionario.getFuncionarios()).containsOnly(funcionarioBack);

        controlePagamentoFuncionario.setFuncionarios(new HashSet<>());
        assertThat(controlePagamentoFuncionario.getFuncionarios()).doesNotContain(funcionarioBack);
    }
}
