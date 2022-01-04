package com.wagner.tqi.loan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wagner.tqi.person.entity.Person;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long valor;

    @Column(nullable = false)
    private LocalDate primeiraParcela;

    @Column(nullable = false)
    private LocalDate dataPedido;

    @Column(nullable = false)
    private int qtdParcelas;

    @ManyToOne
    private Person cliente;

    private int codigo;





}
