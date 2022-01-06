package com.wagner.tqi.person.service;

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

    @Test
    void shouldCreatePerson() {
        PersonDTO personDTO = createFakeDTO(); //método static da classe PersonUtils
        Person expectedSavedPerson = createFakeEntity(); //método static da classe PersonUtils


        Mockito.when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);

        MessageResponseDTO expectedSuccessMessage = createExpectedSuccessMessage(expectedSavedPerson.getId());

        MessageResponseDTO successMessage = personService.createPerson(personDTO);

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


    private MessageResponseDTO createExpectedSuccessMessage(Long savedPersonId){
        return MessageResponseDTO.builder()
                .mensagem("Created person with ID: " + savedPersonId)
                .build();
    }

}
