package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.exception.LoanNotFoundException;
import com.wagner.tqi.exception.LoanBadRequestException;
import com.wagner.tqi.loan.entity.LoanDetailedList;
import com.wagner.tqi.loan.entity.LoanSimpleList;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.person.repository.PersonRepository;
import com.wagner.tqi.user.ApplicationUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private LoanRepository loanRepository;
    private PersonRepository personRepository;

    //cria empréstimo somente para o usuário logado no sistema
    @Transactional //Uma transação garante que qualquer processo deva ser executado com êxito, é “tudo ou nada” (princípio da atomicidade).
    public void createLoanByLoggedUser(LoanDTO loanDTO) throws PersonNotFoundException, LoanBadRequestException {

        // resgata email do usuário que está logado no sistema
        String email = ApplicationUserDetailsService.getLoggedUser();

        // se usuário não estiver logado no sistema, lança exceção
        if (email == null) throw new LoanBadRequestException("Usuário precisa estar autenticado.");

        // faz busca do objeto Person com o email do usuário
        Optional<Person> optionalPerson = personRepository.findByEmail(email);

        // se o objeto Person não existir, lança exceção
        Person person = optionalPerson.orElseThrow(() -> new PersonNotFoundException("Para criar empréstimo é preciso estar logado no sistema"));

        //estabelece a data do pedido como a data atual
        loanDTO.setDataPedido(LocalDate.now());

        // verifica se o empréstimo está de acordo com as regras do negócio
        isLoanValid(loanDTO);

        // cria nova solicitação de empréstimo
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



    // busca todos os empréstimos do usuário logado no sistema
    // retorna JPA Projection com o código do empréstimo, o valor e a quantidade de parcelas.
    public List<LoanSimpleList> getLoansByLoggedUser() throws LoanBadRequestException {

        // busca qual usuário está logado no sistema
        String email = ApplicationUserDetailsService.getLoggedUser();

        // se usuário não estiver logado no sistema, lança exceção
        if (email == null) throw new LoanBadRequestException("Usuário precisa estar autenticado.");

        // busca todos os empréstimos do usuário logado no sistema
        List<LoanSimpleList> loansByClienteEmail = loanRepository.findByCliente_Email(email, LoanSimpleList.class);

        return loansByClienteEmail;
    }

    // busca todos os empréstimos do usuário logado no sistema
    // retorna JPA Projection com código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.
    public List<LoanDetailedList> getDetailedLoansByLoggedUser() throws LoanBadRequestException {
        // busca qual usuário está logado no sistema
        String email = ApplicationUserDetailsService.getLoggedUser();

        // se usuário não estiver logado no sistema, lança exceção
        if (email == null) throw new LoanBadRequestException("Usuário precisa estar autenticado.");

        // busca todos os empréstimos do usuário logado no sistema
        List<LoanDetailedList> detailedLoans = loanRepository.findByCliente_Email(email, LoanDetailedList.class);

        return detailedLoans;

    }


    // verifica se uma solicitação de empréstimo existe
    private Loan verifyIfLoanExists(Long idloan) throws LoanNotFoundException {

        return loanRepository.findById(idloan)
                .orElseThrow(()->new LoanNotFoundException(idloan));
    }

    // verifica se empréstimo está de acordo com as regras do negócio
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


}
