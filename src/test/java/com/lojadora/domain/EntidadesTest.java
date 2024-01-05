package com.lojadora.domain;

import static com.lojadora.domain.EntidadesTestSamples.*;
import static com.lojadora.domain.FluxoCaixaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EntidadesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entidades.class);
        Entidades entidades1 = getEntidadesSample1();
        Entidades entidades2 = new Entidades();
        assertThat(entidades1).isNotEqualTo(entidades2);

        entidades2.setId(entidades1.getId());
        assertThat(entidades1).isEqualTo(entidades2);

        entidades2 = getEntidadesSample2();
        assertThat(entidades1).isNotEqualTo(entidades2);
    }

    @Test
    void fluxoCaixaTest() throws Exception {
        Entidades entidades = getEntidadesRandomSampleGenerator();
        FluxoCaixa fluxoCaixaBack = getFluxoCaixaRandomSampleGenerator();

        entidades.addFluxoCaixa(fluxoCaixaBack);
        assertThat(entidades.getFluxoCaixas()).containsOnly(fluxoCaixaBack);
        assertThat(fluxoCaixaBack.getEntidades()).containsOnly(entidades);

        entidades.removeFluxoCaixa(fluxoCaixaBack);
        assertThat(entidades.getFluxoCaixas()).doesNotContain(fluxoCaixaBack);
        assertThat(fluxoCaixaBack.getEntidades()).doesNotContain(entidades);

        entidades.fluxoCaixas(new HashSet<>(Set.of(fluxoCaixaBack)));
        assertThat(entidades.getFluxoCaixas()).containsOnly(fluxoCaixaBack);
        assertThat(fluxoCaixaBack.getEntidades()).containsOnly(entidades);

        entidades.setFluxoCaixas(new HashSet<>());
        assertThat(entidades.getFluxoCaixas()).doesNotContain(fluxoCaixaBack);
        assertThat(fluxoCaixaBack.getEntidades()).doesNotContain(entidades);
    }
}
