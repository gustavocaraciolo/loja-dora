package com.lojadora.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ControleEntregasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ControleEntregas getControleEntregasSample1() {
        return new ControleEntregas().id(1L).descricao("descricao1").address("address1");
    }

    public static ControleEntregas getControleEntregasSample2() {
        return new ControleEntregas().id(2L).descricao("descricao2").address("address2");
    }

    public static ControleEntregas getControleEntregasRandomSampleGenerator() {
        return new ControleEntregas()
            .id(longCount.incrementAndGet())
            .descricao(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}
