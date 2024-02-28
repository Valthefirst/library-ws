package org.nneji.libraryws.loanssubdomain.businesslayer;

import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanRequestModel;
import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanResponseModel;

import java.util.List;

public interface LoanService {
    List<LoanResponseModel> getAllLoansForPatron(String patronId);

    LoanResponseModel getLoanForPatron(String patronId, String loanId);

    LoanResponseModel addLoanForPatron(String patronId, LoanRequestModel loanRequestModel);

    LoanResponseModel updateLoanForPatron(String patronId, LoanRequestModel loanRequestModel, String loanId);

    void deleteLoanForPatron(String patronId, String loanId);
}
