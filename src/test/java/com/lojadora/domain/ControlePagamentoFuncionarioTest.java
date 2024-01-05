package com.lojadora.domain;

import static com.lojadora.domain.ControlePagamentoFuncionarioTestSamples.*;
import static com.lojadora.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
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

        controlePagamentoFuncionario.setFuncionario(funcionarioBack);
        assertThat(controlePagamentoFuncionario.getFuncionario()).isEqualTo(funcionarioBack);

        controlePagamentoFuncionario.funcionario(null);
        assertThat(controlePagamentoFuncionario.getFuncionario()).isNull();
    }
}
