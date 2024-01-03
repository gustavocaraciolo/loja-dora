package com.lojadora.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ControleAjustesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ControleAjustes getControleAjustesSample1() {
        return new ControleAjustes().id(1L).qtdPecas(1).descricao("descricao1");
    }

    public static ControleAjustes getControleAjustesSample2() {
        return new ControleAjustes().id(2L).qtdPecas(2).descricao("descricao2");
    }

    public static ControleAjustes getControleAjustesRandomSampleGenerator() {
        return new ControleAjustes()
            .id(longCount.incrementAndGet())
            .qtdPecas(intCount.incrementAndGet())
            .descricao(UUID.randomUUID().toString());
    }
}
