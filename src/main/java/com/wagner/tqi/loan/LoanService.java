package com.wagner.tqi.loan;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private LoanRepository loanRepository;

    public void createLoan(Loan loan){
        loanRepository.save(loan);
    }

    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

}
