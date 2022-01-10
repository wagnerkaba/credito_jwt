package com.wagner.tqi.person.service;


import com.wagner.tqi.exception.LoanBadRequestException;
import com.wagner.tqi.exception.LoanNotFoundException;
import com.wagner.tqi.exception.PersonBadRequestException;
import com.wagner.tqi.loan.LoanService;
import com.wagner.tqi.security.ApplicationUserRole;
import com.wagner.tqi.user.ApplicationUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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

    private LoanService loanService;


    // método para criar novos usuários
    public MessageResponseDTO createPerson(PersonDTO personDTO, boolean isAnonymous) throws PersonBadRequestException {
        String user = null;
        Person savedPerson = null;

        Person personToSave = personMapper.toModel(personDTO);

        // se a variavel isAnonymous é verdadeira, significa que a origem do pedido foi o endpoint "/register"
        // nesse caso, o usuário criado é automaticamente do ROLE CUSTOMER
        if (isAnonymous) personToSave.setUserRole(ApplicationUserRole.CUSTOMER);

        // tenta salvar pessoa no banco de dados
        savedPerson = tryToSave(personToSave);


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


    public MessageResponseDTO deleteById(Long id) throws PersonNotFoundException, PersonBadRequestException {


        // verifica se Person com o id desejado existe
        Person person = verifyIfExists(id);

        // se a pessoa possui empréstimos em seu nome, ela não pode ser apagada
        if (loanService.clientHaveLoans(person.getEmail())) {
            throw new PersonBadRequestException("PESSOA NÃO PODE SER APAGADA PORQUE POSSUI EMPRÉSTIMOS EM SEU NOME");
        }


        // Remove Person do banco de dados
        personRepository.deleteById(id);

        // verifica o usuário logado para colocar no response
        String authenticatedUser = ApplicationUserDetailsService.getAuthenticatedUser();

        return createMessageResponseDTO(id, "Removido pessoa com ID:", authenticatedUser);
    }


    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException, PersonBadRequestException {

        verifyIfExists(id);

        // verifica se existe conflito entre id e PersonDTO.getId()
        if (personDTO.getId() != id) throw new PersonBadRequestException("USUÁRIO NÃO PODE SER ALTERADO. EXISTE ERRO NO ID INFORMADO.");


        Person personToUpdate = personMapper.toModel(personDTO);

        // tenta salvar person no banco de dados
        Person updatedPerson = tryToSave(personToUpdate);

        // verifica o usuário logado para colocar no response
        String authenticatedUser = ApplicationUserDetailsService.getAuthenticatedUser();

        return createMessageResponseDTO(updatedPerson.getId(), "Alterado pessoa com ID:", authenticatedUser);
    }


    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id.toString()));
    }


    // tenta salvar Person no banco de dados. Se não conseguir, lança exceção
    private Person tryToSave(Person personToSave) throws PersonBadRequestException {
        Person savedPerson = new Person();

        try {
             savedPerson = personRepository.save(personToSave);
             return savedPerson;
        } catch (Exception e){

            //se houve erro ao salvar person no banco de dados, a causa mais provável é que já existe usuário com os mesmos dados
            throw new PersonBadRequestException("POSSÍVEL DUPLICAÇÃO DE DADOS: VERIFIQUE SE USUÁRIO COM MESMOS DADOS JÁ FOI CRIADO");
        }
    }



}
