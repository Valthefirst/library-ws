package org.nneji.libraryws.loanssubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer>{

    Loan findByLoanIdentifier_LoanId(String loanId);
    List<Loan> findAllByPatronIdentifier_PatronId(String patronId);
}
