package com.wagner.tqi.loan.entity;
// JPA Projection
// Objetivo: retornar apenas o código do empréstimo, o valor e a quantidade de parcelas.
// Mais informações: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
public interface LoanSimpleList {
    String getCodigo();
    Long getValor();
    int getQtdParcelas();

}
