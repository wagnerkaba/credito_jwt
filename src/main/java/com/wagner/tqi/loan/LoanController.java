package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.exception.LoanNotFoundException;
import com.wagner.tqi.exception.LoanBadRequestException;
import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.loan.entity.LoanDetailedList;
import com.wagner.tqi.loan.entity.LoanSimpleList;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
@AllArgsConstructor(onConstructor =  @__(@Autowired))
public class LoanController {

    private LoanService loanService;

    // busca empréstimos de todos os usuários
    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    // busca todos os empréstimos do usuário logado no sistema
    // Na listagem, retorna o código do empréstimo, o valor e a quantidade de parcelas.
    @GetMapping("/listaporcliente")
    public List<LoanSimpleList> getLoansByLoggedUser() throws LoanBadRequestException {
        return loanService.getLoansByLoggedUser();
    }

    // busca todos os empréstimos do usuário logado no sistema
    // retorna JPA Projection com código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.
    @GetMapping("/listaporcliente/detalhes")
    public List<LoanDetailedList> getDetailedLoansByLoggedUser() throws LoanBadRequestException {
        return loanService.getDetailedLoansByLoggedUser();
    }



    // faz solicitação de novo empréstimo
    @PostMapping
    public void createLoanByLoggedUser(@RequestBody LoanDTO loanDTO) throws PersonNotFoundException, LoanBadRequestException {
        loanService.createLoanByLoggedUser(loanDTO);
    }

    // apaga empréstimo
    @DeleteMapping("/{idloan}")
    public void deleteLoanById(@PathVariable Long idloan) throws LoanNotFoundException {
        loanService.deleteLoanById(idloan);
    }


}
