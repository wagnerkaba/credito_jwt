package com.wagner.tqi.loan.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LoanDTO {
    private Long id;
    private Long valor;
    private LocalDate primeiraParcela;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate dataPedido;
    private int qtdParcelas;
    private int codigo;
    private Long idPerson;
}
