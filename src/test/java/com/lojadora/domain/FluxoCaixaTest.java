package com.lojadora.domain;

import static com.lojadora.domain.EntidadesTestSamples.*;
import static com.lojadora.domain.FluxoCaixaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FluxoCaixaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FluxoCaixa.class);
        FluxoCaixa fluxoCaixa1 = getFluxoCaixaSample1();
        FluxoCaixa fluxoCaixa2 = new FluxoCaixa();
        assertThat(fluxoCaixa1).isNotEqualTo(fluxoCaixa2);

        fluxoCaixa2.setId(fluxoCaixa1.getId());
        assertThat(fluxoCaixa1).isEqualTo(fluxoCaixa2);

        fluxoCaixa2 = getFluxoCaixaSample2();
        assertThat(fluxoCaixa1).isNotEqualTo(fluxoCaixa2);
    }

    @Test
    void entidadesTest() throws Exception {
        FluxoCaixa fluxoCaixa = getFluxoCaixaRandomSampleGenerator();
        Entidades entidadesBack = getEntidadesRandomSampleGenerator();

        fluxoCaixa.addEntidades(entidadesBack);
        assertThat(fluxoCaixa.getEntidades()).containsOnly(entidadesBack);

        fluxoCaixa.removeEntidades(entidadesBack);
        assertThat(fluxoCaixa.getEntidades()).doesNotContain(entidadesBack);

        fluxoCaixa.entidades(new HashSet<>(Set.of(entidadesBack)));
        assertThat(fluxoCaixa.getEntidades()).containsOnly(entidadesBack);

        fluxoCaixa.setEntidades(new HashSet<>());
        assertThat(fluxoCaixa.getEntidades()).doesNotContain(entidadesBack);
    }
}
