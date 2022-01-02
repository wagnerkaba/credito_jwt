package com.wagner.tqi.loan;

import com.wagner.tqi.person.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long valor;

    @Column(nullable = false)
    private LocalDate primeiraParcela;

    @Column(nullable = false)
    private int qtdParcelas;

    @ManyToOne
    private Person cliente;

    private int codigo;





}
