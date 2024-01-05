package com.lojadora.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ControleDiarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ControleDiario getControleDiarioSample1() {
        return new ControleDiario().id(1L).cliente("cliente1");
    }

    public static ControleDiario getControleDiarioSample2() {
        return new ControleDiario().id(2L).cliente("cliente2");
    }

    public static ControleDiario getControleDiarioRandomSampleGenerator() {
        return new ControleDiario().id(longCount.incrementAndGet()).cliente(UUID.randomUUID().toString());
    }
}
