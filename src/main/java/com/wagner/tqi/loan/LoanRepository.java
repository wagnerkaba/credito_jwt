package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Long> {


    // How to write a Spring Data JPA repository interface method signature that will let me find entities
    // with a property of an embedded object in that entity?
    // Answer 1: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions
    // Answer 2: https://stackoverflow.com/questions/24441411/spring-data-jpa-find-by-embedded-object-property
    List<Loan> findByCliente_Email(String email);
}
