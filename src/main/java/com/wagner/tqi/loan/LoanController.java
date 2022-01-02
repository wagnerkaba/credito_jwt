package com.wagner.tqi.loan;

import com.wagner.tqi.loan.entity.Loan;
import com.wagner.tqi.loan.entity.LoanDTO;
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

    @PostMapping("/{idperson}")
    public void createLoan(@PathVariable Long idperson, @RequestBody LoanDTO loanDTO){
        loanService.createLoan(loanDTO, idperson);
    }

}
