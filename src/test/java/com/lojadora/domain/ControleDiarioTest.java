package com.lojadora.domain;

import static com.lojadora.domain.ControleDiarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lojadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ControleDiarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleDiario.class);
        ControleDiario controleDiario1 = getControleDiarioSample1();
        ControleDiario controleDiario2 = new ControleDiario();
        assertThat(controleDiario1).isNotEqualTo(controleDiario2);

        controleDiario2.setId(controleDiario1.getId());
        assertThat(controleDiario1).isEqualTo(controleDiario2);

        controleDiario2 = getControleDiarioSample2();
        assertThat(controleDiario1).isNotEqualTo(controleDiario2);
    }
}
