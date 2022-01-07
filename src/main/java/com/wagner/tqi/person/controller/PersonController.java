package com.wagner.tqi.person.controller;


import com.wagner.tqi.exception.PersonBadRequestException;
import com.wagner.tqi.person.dto.request.PersonDTO;
import com.wagner.tqi.response.MessageResponseDTO;
import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.person.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor =  @__(@Autowired))
public class PersonController {

    private PersonService personService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<PersonDTO> listAll(){

        return personService.listAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public PersonDTO findById(@PathVariable Long id) throws PersonNotFoundException {
        return personService.findById(id);
    }



    //endpoint para ADMIN criar cadastro de pessoas
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('person:write')")
    public MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO) throws PersonBadRequestException {

        return personService.createPerson(personDTO, false);
    }

    // endpoint para qualquer pessoa criar um cadastro
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO registerPerson(@RequestBody @Valid PersonDTO personDTO) throws PersonBadRequestException {
        return personService.createPerson(personDTO, true);
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('person:write')")
    public MessageResponseDTO deleteById(@PathVariable Long id) throws PersonNotFoundException {
        return personService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('person:write')")
    public MessageResponseDTO updateById(@PathVariable  Long id,@RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException, PersonBadRequestException {
        return personService.updateById(id, personDTO);


    }





}
