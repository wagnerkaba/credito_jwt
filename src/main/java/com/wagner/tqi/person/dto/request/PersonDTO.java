package com.wagner.tqi.person.dto.request;

import com.wagner.tqi.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private long id;

    @NotEmpty
    @Size(min=2, max=100)
    private String firstName;

    @NotEmpty
    @Size(min=2, max=100)
    private String lastName;

    @NotEmpty
    @CPF
    private String cpf;

    @NotEmpty
    private String email;

    private String password;

    private String birthDate;

    @NotEmpty
    @Valid
    private List<PhoneDTO> phones;

    private ApplicationUserRole userRole;

}
