package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.exception.LoanNotFoundException;
import com.wagner.tqi.exception.LoanNotValidException;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private LoanRepository loanRepository;
    private PersonRepository personRepository;

    public void createLoan(LoanDTO loanDTO) throws PersonNotFoundException, LoanNotValidException {


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

    private boolean isLoanValid(LoanDTO loanDTO) throws LoanNotValidException {

        // o empréstimo pode ter no máximo 60 parcelas
        if (loanDTO.getQtdParcelas() < 1 || loanDTO.getQtdParcelas() > 60) {
            throw new LoanNotValidException("O empréstimo deve ter entre 1 e 60 parcelas");
        }

        // a data da primeira parcela deve ser no máximo 3 meses após o dia atual
        LocalDate prazoMaximoEmprestimo = loanDTO.getDataPedido().plusMonths(3);
        if (loanDTO.getPrimeiraParcela().isBefore(loanDTO.getDataPedido()) || loanDTO.getPrimeiraParcela().isAfter(prazoMaximoEmprestimo)){
            throw new LoanNotValidException("A data da primeira parcela deve ser desde o dia do pedido até o prazo máximo de três meses");
        }



        return true;
    }


    public List<Loan> getLoansByClienteEmail() {

        List<Loan> loansByPersonEmail = loanRepository.findByCliente_Email("admin@gmail.com");



        return loansByPersonEmail;



    }
}
