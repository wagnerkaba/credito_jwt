package com.wagner.tqi.person.service;

import com.wagner.tqi.exception.PersonBadRequestException;
import com.wagner.tqi.user.ApplicationUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.wagner.tqi.person.dto.request.PersonDTO;
import com.wagner.tqi.response.MessageResponseDTO;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.person.mapper.PersonMapper;
import com.wagner.tqi.person.repository.PersonRepository;

import java.util.List;

import static org.mockito.Mockito.any;
import static com.wagner.tqi.person.utils.PersonUtils.*;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Mock
    private ApplicationUserDetailsService applicationUserDetailsService;




    @Test
    void shouldCreatePerson() throws PersonBadRequestException {
        PersonDTO personDTO = createFakeDTO(); //método static da classe PersonUtils
        Person expectedSavedPerson = createFakeEntity(); //método static da classe PersonUtils


        Mockito.when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);

        Mockito.when(applicationUserDetailsService.getAuthenticatedUser()).thenReturn(expectedSavedPerson.getEmail());

        MessageResponseDTO expectedSuccessMessage = createExpectedSuccessMessage(expectedSavedPerson.getId() , expectedSavedPerson.getEmail());

        MessageResponseDTO successMessage = personService.createPerson(personDTO, true);

        Assertions.assertEquals(expectedSuccessMessage, successMessage);
    }

    @Test
    void shouldListAll(){
        List<Person> fakePersonList = createFakePersonList();
        List<PersonDTO> fakePersonDTOList = createFakePersonDTOList();
        Mockito.when(personRepository.findAll()).thenReturn(fakePersonList);

        List<PersonDTO> listAllSuccess = personService.listAll();
        Assertions.assertEquals(listAllSuccess, fakePersonDTOList);


    }


    private MessageResponseDTO createExpectedSuccessMessage(Long id, String email){
        return MessageResponseDTO.builder()
                .mensagem("Criado uma pessoa com ID: " + id)
                .autor(email)
                .build();
    }

}
