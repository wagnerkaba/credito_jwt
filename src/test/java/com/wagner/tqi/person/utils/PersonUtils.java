package com.wagner.tqi.person.utils;

import com.wagner.tqi.person.dto.request.PersonDTO;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.person.mapper.PersonMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PersonUtils {
    public static final String FIRST_NAME = "Ricardo";
    public static final String LAST_NAME = "Da Silva";
    public static final String CPF_NUMBER = "369.333.878-79";
    public static final long PERSON_ID = 1L;
    public static final String EMAIL = "teste@gmail.com";
    public static final String PASSWORD = "PASSWORD";

    public static final Long RENDA = 2000L;
    public static final String RG = "456468";

    public static final String ENDERECO = "RUA GOIAS";
    public static final String ENDERECO_NUMBER = "457";
    public static final String BAIRRO = "VILA RECREIO";

    public static final String CEP = "86058-458";
    public static final String CIDADE = "RIO DE JANEIRO";
    public static final String ESTADO = "RIO DE JANEIRO";
    public static final String PAIS = "BRASIL";





    private static PersonMapper personMapper = PersonMapper.INSTANCE;


    public static PersonDTO createFakeDTO(){
        return PersonDTO.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .email(EMAIL)
                .password(PASSWORD)
                .renda(RENDA)
                .rg(RG)
                .endereco(ENDERECO)
                .enderecoNumero(ENDERECO_NUMBER)
                .bairro(BAIRRO)
                .cep(CEP)
                .cidade(CIDADE)
                .estado(ESTADO)
                .pais(PAIS)
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static Person createFakeEntity(){
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .email(EMAIL)
                .password(PASSWORD)
                .renda(RENDA)
                .rg(RG)
                .endereco(ENDERECO)
                .enderecoNumero(ENDERECO_NUMBER)
                .bairro(BAIRRO)
                .cep(CEP)
                .cidade(CIDADE)
                .estado(ESTADO)
                .pais(PAIS)
                .phones(Collections.singletonList(PhoneUtils.createFakeEntity()))
                .build();
    }

    public static List<Person> createFakePersonList(){
        Person fake = createFakeEntity();
        return List.of(fake);
    }

    public static List<PersonDTO> createFakePersonDTOList(){
        Person fake = createFakeEntity();
        PersonDTO fakeDTO = personMapper.toDTO(fake);

        return List.of(fakeDTO);
    }


}
