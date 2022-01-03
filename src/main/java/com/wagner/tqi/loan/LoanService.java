package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.exception.LoanNotFoundException;
import com.wagner.tqi.exception.LoanBadRequestException;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private LoanRepository loanRepository;
    private PersonRepository personRepository;

    public void createLoan(LoanDTO loanDTO) throws PersonNotFoundException, LoanBadRequestException {


        Optional<Person> optionalPerson = personRepository.findById(loanDTO.getIdPerson());

        Person person = optionalPerson.orElseThrow(() -> new PersonNotFoundException(loanDTO.getIdPerson().toString()));

        //a data do pedido é a data atual
        loanDTO.setDataPedido(LocalDate.now());

        isLoanValid(loanDTO);

        Loan loan = new Loan(
                null,
                loanDTO.getValor(),
                loanDTO.getPrimeiraParcela(),
                loanDTO.getDataPedido(),
                loanDTO.getQtdParcelas(),
                person,
                1 //Código do empréstimo a ser definito pelas regras de negócio
        );

        loanRepository.save(loan);
    }

    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    public void deleteLoanById(Long idloan) throws LoanNotFoundException {

        verifyIfLoanExists(idloan);
        loanRepository.deleteById(idloan);

    }

    private Loan verifyIfLoanExists(Long idloan) throws LoanNotFoundException {

        return loanRepository.findById(idloan)
                .orElseThrow(()->new LoanNotFoundException(idloan));
    }

    private boolean isLoanValid(LoanDTO loanDTO) throws LoanBadRequestException {

        // o empréstimo pode ter no máximo 60 parcelas
        if (loanDTO.getQtdParcelas() < 1 || loanDTO.getQtdParcelas() > 60) {
            throw new LoanBadRequestException("O empréstimo deve ter entre 1 e 60 parcelas");
        }

        // a data da primeira parcela deve ser no máximo 3 meses após o dia atual
        LocalDate prazoMaximoEmprestimo = loanDTO.getDataPedido().plusMonths(3);
        if (loanDTO.getPrimeiraParcela().isBefore(loanDTO.getDataPedido()) || loanDTO.getPrimeiraParcela().isAfter(prazoMaximoEmprestimo)){
            throw new LoanBadRequestException("A data da primeira parcela deve ser desde o dia do pedido até o prazo máximo de três meses");
        }



        return true;
    }

    // busca todos os emréstimos do usuário logado no sistema
    public List<Loan> getLoansByClienteEmail() throws LoanBadRequestException {

        // busca qual usuário está logado no sistema
        String email = getAuthenticatedUser();

        // se usuário não estiver logado no sistema, lança exceção
        if (email == null) throw new LoanBadRequestException("Usuário precisa estar autenticado.");

        // busca todos os empréstimos do usuário logado no sistema
        List<Loan> loansByPersonEmail = loanRepository.findByCliente_Email(email);

        return loansByPersonEmail;
    }

    // busca email do usuário logado no sistema
    // se usuário não estiver logado, retorna null
    private String getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return auth.getName();
        }
        return null;
    }

}
