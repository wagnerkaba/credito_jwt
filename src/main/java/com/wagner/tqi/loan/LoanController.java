package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.exception.LoanNotFoundException;
import com.wagner.tqi.exception.LoanBadRequestException;
import com.wagner.tqi.exception.PersonNotFoundException;
import com.wagner.tqi.loan.entity.LoanDetailedList;
import com.wagner.tqi.loan.entity.LoanSimpleList;
import com.wagner.tqi.response.MessageResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
@AllArgsConstructor(onConstructor =  @__(@Autowired))
public class LoanController {

    private LoanService loanService;

    // busca empréstimos de todos os usuários
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    // busca todos os empréstimos do usuário logado no sistema
    // Na listagem, retorna o código do empréstimo, o valor e a quantidade de parcelas.
    @GetMapping("/listaporcliente")
    public List<LoanSimpleList> getLoansByAuthenticatedUser() throws LoanBadRequestException {
        return loanService.getLoansByAuthenticatedUser();
    }

    // busca todos os empréstimos do usuário logado no sistema
    // retorna JPA Projection com código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.
    @GetMapping("/listaporcliente/detalhes")
    public List<LoanDetailedList> getDetailedLoansByAuthenticatedUser() throws LoanBadRequestException {
        return loanService.getDetailedLoansByAuthenticatedUser();
    }



    // faz solicitação de novo empréstimo
    @PostMapping
    public MessageResponseDTO createLoanByAuthenticatedUser(@RequestBody LoanDTO loanDTO) throws PersonNotFoundException, LoanBadRequestException {
        return loanService.createLoanByAuthenticatedUser(loanDTO);
    }

    // apaga empréstimo
    @DeleteMapping("/{idloan}")
    @PreAuthorize("hasAuthority('person:write')")
    public MessageResponseDTO deleteLoanById(@PathVariable Long idloan) throws LoanNotFoundException {
        return loanService.deleteLoanById(idloan);
    }


}
