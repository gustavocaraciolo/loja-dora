package com.lojadora.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class FluxoCaixaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FluxoCaixa getFluxoCaixaSample1() {
        return new FluxoCaixa().id(1L);
    }

    public static FluxoCaixa getFluxoCaixaSample2() {
        return new FluxoCaixa().id(2L);
    }

    public static FluxoCaixa getFluxoCaixaRandomSampleGenerator() {
        return new FluxoCaixa().id(longCount.incrementAndGet());
    }
}
