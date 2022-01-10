package com.wagner.tqi.loan.entity;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

// JPA Projection
// Objetivo: retornar código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.
// Mais informações: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
public interface LoanDetailedList{
    String getCodigo();
    Long getValor();
    int getQtdParcelas();
    LocalDate getPrimeiraParcela();

    // Email está dentro de Person. Por isso, é necessário a anotação abaixo para resgata-lo
    // vide: https://www.baeldung.com/spring-data-rest-projections-excerpts
    @Value("#{target.getCliente().getEmail()}")
    String getEmail();

    @Value("#{target.getCliente().getRenda()}")
    Long getRenda();


}
