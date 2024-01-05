package com.lojadora.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EntidadesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Entidades getEntidadesSample1() {
        return new Entidades().id(1L).nome("nome1").endereco("endereco1").telefone(1);
    }

    public static Entidades getEntidadesSample2() {
        return new Entidades().id(2L).nome("nome2").endereco("endereco2").telefone(2);
    }

    public static Entidades getEntidadesRandomSampleGenerator() {
        return new Entidades()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .endereco(UUID.randomUUID().toString())
            .telefone(intCount.incrementAndGet());
    }
}
