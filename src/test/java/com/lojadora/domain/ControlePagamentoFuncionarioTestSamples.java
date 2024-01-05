package com.lojadora.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ControlePagamentoFuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ControlePagamentoFuncionario getControlePagamentoFuncionarioSample1() {
        return new ControlePagamentoFuncionario().id(1L);
    }

    public static ControlePagamentoFuncionario getControlePagamentoFuncionarioSample2() {
        return new ControlePagamentoFuncionario().id(2L);
    }

    public static ControlePagamentoFuncionario getControlePagamentoFuncionarioRandomSampleGenerator() {
        return new ControlePagamentoFuncionario().id(longCount.incrementAndGet());
    }
}
