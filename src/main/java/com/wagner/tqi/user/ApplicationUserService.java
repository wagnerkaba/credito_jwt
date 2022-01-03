package com.wagner.tqi.user;

import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.person.dto.request.PersonDTO;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.person.repository.PersonRepository;
import com.wagner.tqi.security.ApplicationUserRole;
import lombok.SneakyThrows;
import org.hibernate.boot.model.source.spi.PluralAttributeElementSourceOneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username){

        Optional<Person> optionalPerson = personRepository.findByEmail(username);

        // se usuário não for encontrado, lança exceção UsernameNotFoundException
        // OBS: Não utilizar PersonNotFoundException aqui.
        // UsernameNotFoundException mostra mensagem "Bad Credentials". Ou seja, mensagem genérica.
        // PersonNotFoundException deixa explícito que usuário não existe
        // Em tela de login, é melhor não expor o motivo do usuário não conseguir logar
        Person person = optionalPerson.orElseThrow(() -> new UsernameNotFoundException(username));

        ApplicationUserDetails applicationUserDetails = personToUserDetails(person);

        return applicationUserDetails;
    }

    // converte objeto Person em ApplicationUserDetails
    private ApplicationUserDetails personToUserDetails(Person person){

        ApplicationUserDetails applicationUserDetails = new ApplicationUserDetails(
            person.getUserRole().getGrantedAuthorities(),
            person.getEmail(),
            passwordEncoder.encode(person.getPassword()),
            true,
            true,
            true,
            true
        );
        return applicationUserDetails;
    }


}
