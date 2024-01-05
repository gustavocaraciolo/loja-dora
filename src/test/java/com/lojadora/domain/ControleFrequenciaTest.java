package com.lojadora.domain;

import static com.lojadora.domain.ControleFrequenciaTestSamples.*;
import static com.lojadora.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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

        controleFrequencia.addFuncionario(funcionarioBack);
        assertThat(controleFrequencia.getFuncionarios()).containsOnly(funcionarioBack);

        controleFrequencia.removeFuncionario(funcionarioBack);
        assertThat(controleFrequencia.getFuncionarios()).doesNotContain(funcionarioBack);

        controleFrequencia.funcionarios(new HashSet<>(Set.of(funcionarioBack)));
        assertThat(controleFrequencia.getFuncionarios()).containsOnly(funcionarioBack);

        controleFrequencia.setFuncionarios(new HashSet<>());
        assertThat(controleFrequencia.getFuncionarios()).doesNotContain(funcionarioBack);
    }
}
