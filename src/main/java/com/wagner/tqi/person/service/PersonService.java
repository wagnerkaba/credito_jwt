package com.wagner.tqi.person.service;


import com.wagner.tqi.user.ApplicationUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wagner.tqi.person.dto.request.PersonDTO;
import com.wagner.tqi.response.MessageResponseDTO;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.person.mapper.PersonMapper;
import com.wagner.tqi.person.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

import static com.wagner.tqi.response.MessageResponseDTO.createMessageResponseDTO;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;




    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);

        // verifica o usuário logado para colocar no response
        String authenticatedUser = ApplicationUserDetailsService.getAuthenticatedUser();

        return createMessageResponseDTO(savedPerson.getId(), "Criado pessoa com ID: ", authenticatedUser);
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();

        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());


    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {

        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }


    public MessageResponseDTO deleteById(Long id) throws PersonNotFoundException {

        verifyIfExists(id);
        personRepository.deleteById(id);

        // verifica o usuário logado para colocar no response
        String authenticatedUser = ApplicationUserDetailsService.getAuthenticatedUser();

        return createMessageResponseDTO(id, "Removido pessoa com ID:", authenticatedUser);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {

        verifyIfExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);

        // verifica o usuário logado para colocar no response
        String authenticatedUser = ApplicationUserDetailsService.getAuthenticatedUser();

        return createMessageResponseDTO(updatedPerson.getId(), "Alterado pessoa com ID:", authenticatedUser);
    }


    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id.toString()));
    }



}
