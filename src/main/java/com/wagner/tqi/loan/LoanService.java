package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.loan.exception.LoanNotFoundException;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.person.exception.PersonNotFoundException;
import com.wagner.tqi.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private LoanRepository loanRepository;
    private PersonRepository personRepository;

    public void createLoan(LoanDTO loanDTO) throws PersonNotFoundException {


        Optional<Person> optionalPerson = personRepository.findById(loanDTO.getIdPerson());

        Person person = optionalPerson.orElseThrow(() -> new PersonNotFoundException(loanDTO.getIdPerson()));


        Loan loan = new Loan(
                null,
                loanDTO.getValor(),
                loanDTO.getPrimeiraParcela(),
                loanDTO.getQtdParcelas(),
                person,
                1
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


}
