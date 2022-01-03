package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.exception.LoanNotFoundException;
import com.wagner.tqi.exception.LoanBadRequestException;
import com.wagner.tqi.exception.PersonNotFoundException;
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
    @GetMapping("/listaporcliente")
    public List<Loan> getLoansByClienteEmail() throws LoanBadRequestException {
        return loanService.getLoansByClienteEmail();
    }


    // faz solicitação de novo empréstimo
    @PostMapping
    public void createLoan(@RequestBody LoanDTO loanDTO) throws PersonNotFoundException, LoanBadRequestException {
        loanService.createLoan(loanDTO);
    }

    // apaga empréstimo
    @DeleteMapping("/{idloan}")
    public void deleteLoanById(@PathVariable Long idloan) throws LoanNotFoundException {
        loanService.deleteLoanById(idloan);
    }


}
