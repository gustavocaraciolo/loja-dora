package com.lojadora.domain;

import static com.lojadora.domain.ControleAjustesTestSamples.*;
import static com.lojadora.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ControleAjustesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleAjustes.class);
        ControleAjustes controleAjustes1 = getControleAjustesSample1();
        ControleAjustes controleAjustes2 = new ControleAjustes();
        assertThat(controleAjustes1).isNotEqualTo(controleAjustes2);

        controleAjustes2.setId(controleAjustes1.getId());
        assertThat(controleAjustes1).isEqualTo(controleAjustes2);

        controleAjustes2 = getControleAjustesSample2();
        assertThat(controleAjustes1).isNotEqualTo(controleAjustes2);
    }

    @Test
    void funcionarioTest() throws Exception {
        ControleAjustes controleAjustes = getControleAjustesRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        controleAjustes.setFuncionario(funcionarioBack);
        assertThat(controleAjustes.getFuncionario()).isEqualTo(funcionarioBack);

        controleAjustes.funcionario(null);
        assertThat(controleAjustes.getFuncionario()).isNull();
    }
}
