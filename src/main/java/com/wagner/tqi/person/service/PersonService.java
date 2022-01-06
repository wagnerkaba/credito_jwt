package com.wagner.tqi.person.service;


import com.wagner.tqi.security.ApplicationUserRole;
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


    // método para criar novos usuários
    public MessageResponseDTO createPerson(PersonDTO personDTO, boolean isAnonymous){
        String user = null;

        Person personToSave = personMapper.toModel(personDTO);

        // se a variavel isAnonymous é verdadeira, significa que a origem do pedido foi o endpoint "/register"
        // nesse caso, o usuário criado é automaticamente do ROLE CUSTOMER
        if (isAnonymous) personToSave.setUserRole(ApplicationUserRole.CUSTOMER);

        Person savedPerson = personRepository.save(personToSave);

        // verifica o usuário logado para colocar o autor do request no response
        user = ApplicationUserDetailsService.getAuthenticatedUser();

        // se user == null isso significa que não tem nenhum usuário autenticado
        // nesse caso, presume-se que o autor do request é o savedPerson
        if (user==null) user = savedPerson.getEmail();

        return createMessageResponseDTO(savedPerson.getId(), "Criado pessoa com ID: ", user);
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
