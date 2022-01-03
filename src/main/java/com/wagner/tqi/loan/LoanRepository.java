package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanSimpleList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {


    // Question 1: How to write a Spring Data JPA repository interface method signature that will let me find entities
    // with a property of an embedded object in that entity?
    // Answer: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions
    // See also: https://stackoverflow.com/questions/24441411/spring-data-jpa-find-by-embedded-object-property
    // Question 2: Is it possible to define what projection type to return from the query method at runtime?
    // Answer: YES. dynamic projection support multiple return types.
    // Search for "dynamic projection" in https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
    <T> List<T> findByCliente_Email(String email, Class<T> tClass);

}
