package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
import com.wagner.tqi.loan.exception.LoanNotFoundException;
import com.wagner.tqi.person.exception.PersonNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
@AllArgsConstructor(onConstructor =  @__(@Autowired))
public class LoanController {

    private LoanService loanService;

    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    @PostMapping
    public void createLoan(@RequestBody LoanDTO loanDTO) throws PersonNotFoundException {
        loanService.createLoan(loanDTO);
    }

    @DeleteMapping("/{idloan}")
    public void deleteLoanById(@PathVariable Long idloan) throws LoanNotFoundException {
        loanService.deleteLoanById(idloan);
    }


}
