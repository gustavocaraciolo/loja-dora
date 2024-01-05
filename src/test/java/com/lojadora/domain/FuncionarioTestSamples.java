package com.lojadora.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Funcionario getFuncionarioSample1() {
        return new Funcionario()
            .id(1L)
            .primeiroNome("primeiroNome1")
            .ultimoNome("ultimoNome1")
            .enderecoLinha1("enderecoLinha11")
            .enderecoLinha2("enderecoLinha21")
            .telefone("telefone1")
            .telefoneEmergencial("telefoneEmergencial1")
            .email("email1")
            .banco("banco1")
            .iban("iban1");
    }

    public static Funcionario getFuncionarioSample2() {
        return new Funcionario()
            .id(2L)
            .primeiroNome("primeiroNome2")
            .ultimoNome("ultimoNome2")
            .enderecoLinha1("enderecoLinha12")
            .enderecoLinha2("enderecoLinha22")
            .telefone("telefone2")
            .telefoneEmergencial("telefoneEmergencial2")
            .email("email2")
            .banco("banco2")
            .iban("iban2");
    }

    public static Funcionario getFuncionarioRandomSampleGenerator() {
        return new Funcionario()
            .id(longCount.incrementAndGet())
            .primeiroNome(UUID.randomUUID().toString())
            .ultimoNome(UUID.randomUUID().toString())
            .enderecoLinha1(UUID.randomUUID().toString())
            .enderecoLinha2(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString())
            .telefoneEmergencial(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .banco(UUID.randomUUID().toString())
            .iban(UUID.randomUUID().toString());
    }
}
