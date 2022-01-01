package com.wagner.tqi.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wagner.tqi.person.entity.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {


    Optional<Person> findByEmail(String email);
}
