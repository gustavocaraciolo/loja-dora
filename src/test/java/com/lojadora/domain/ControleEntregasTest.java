package com.lojadora.domain;

import static com.lojadora.domain.ControleEntregasTestSamples.*;
import static com.lojadora.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ControleEntregasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleEntregas.class);
        ControleEntregas controleEntregas1 = getControleEntregasSample1();
        ControleEntregas controleEntregas2 = new ControleEntregas();
        assertThat(controleEntregas1).isNotEqualTo(controleEntregas2);

        controleEntregas2.setId(controleEntregas1.getId());
        assertThat(controleEntregas1).isEqualTo(controleEntregas2);

        controleEntregas2 = getControleEntregasSample2();
        assertThat(controleEntregas1).isNotEqualTo(controleEntregas2);
    }

    @Test
    void funcionarioTest() throws Exception {
        ControleEntregas controleEntregas = getControleEntregasRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        controleEntregas.addFuncionario(funcionarioBack);
        assertThat(controleEntregas.getFuncionarios()).containsOnly(funcionarioBack);

        controleEntregas.removeFuncionario(funcionarioBack);
        assertThat(controleEntregas.getFuncionarios()).doesNotContain(funcionarioBack);

        controleEntregas.funcionarios(new HashSet<>(Set.of(funcionarioBack)));
        assertThat(controleEntregas.getFuncionarios()).containsOnly(funcionarioBack);

        controleEntregas.setFuncionarios(new HashSet<>());
        assertThat(controleEntregas.getFuncionarios()).doesNotContain(funcionarioBack);
    }
}
