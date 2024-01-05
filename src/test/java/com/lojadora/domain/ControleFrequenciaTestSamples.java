package com.lojadora.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ControleFrequenciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ControleFrequencia getControleFrequenciaSample1() {
        return new ControleFrequencia().id(1L);
    }

    public static ControleFrequencia getControleFrequenciaSample2() {
        return new ControleFrequencia().id(2L);
    }

    public static ControleFrequencia getControleFrequenciaRandomSampleGenerator() {
        return new ControleFrequencia().id(longCount.incrementAndGet());
    }
}
