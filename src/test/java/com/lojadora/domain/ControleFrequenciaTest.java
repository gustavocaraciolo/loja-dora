package com.lojadora.domain;

import static com.lojadora.domain.ControleFrequenciaTestSamples.*;
import static com.lojadora.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ControleFrequenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleFrequencia.class);
        ControleFrequencia controleFrequencia1 = getControleFrequenciaSample1();
        ControleFrequencia controleFrequencia2 = new ControleFrequencia();
        assertThat(controleFrequencia1).isNotEqualTo(controleFrequencia2);

        controleFrequencia2.setId(controleFrequencia1.getId());
        assertThat(controleFrequencia1).isEqualTo(controleFrequencia2);

        controleFrequencia2 = getControleFrequenciaSample2();
        assertThat(controleFrequencia1).isNotEqualTo(controleFrequencia2);
    }

    @Test
    void funcionarioTest() throws Exception {
        ControleFrequencia controleFrequencia = getControleFrequenciaRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        controleFrequencia.setFuncionario(funcionarioBack);
        assertThat(controleFrequencia.getFuncionario()).isEqualTo(funcionarioBack);

        controleFrequencia.funcionario(null);
        assertThat(controleFrequencia.getFuncionario()).isNull();
    }
}
