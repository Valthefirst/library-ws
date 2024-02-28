package org.nneji.libraryws.loanssubdomain.businesslayer;

import org.nneji.libraryws.finessubdomain.datalayer.FineIdentifier;
import org.nneji.libraryws.loanssubdomain.datalayer.Loan;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanIdentifier;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanRepository;
import org.nneji.libraryws.loanssubdomain.datamapperlayer.LoanRequestMapper;
import org.nneji.libraryws.loanssubdomain.datamapperlayer.LoanResponseMapper;
import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanRequestModel;
import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanResponseModel;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronIdentifier;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronRepository;
import org.nneji.libraryws.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;
    private final PatronRepository patronRepository;
    private final LoanResponseMapper loanResponseMapper;
    private final LoanRequestMapper loanRequestMapper;

    public LoanServiceImpl(LoanRepository loanRepository, PatronRepository patronRepository, LoanResponseMapper loanResponseMapper, LoanRequestMapper loanRequestMapper) {
        this.loanRepository = loanRepository;
        this.patronRepository = patronRepository;
        this.loanResponseMapper = loanResponseMapper;
        this.loanRequestMapper = loanRequestMapper;
    }

    @Override
    public List<LoanResponseModel> getAllLoansForPatron(String patronId) {
        if (patronRepository.findByPatronIdentifier_PatronId(patronId) == null) {
            throw new NotFoundException("Invalid patronId: " + patronId);
        }
        return loanResponseMapper.entityListToResponseModelList(
                loanRepository.findAllByPatronIdentifier_PatronId(patronId));
    }

    @Override
    public LoanResponseModel getLoanForPatron(String patronId, String loanId) {
        if (patronRepository.findByPatronIdentifier_PatronId(patronId) == null) {
            throw new NotFoundException("Invalid patronId: " + patronId);
        }

        Loan loan = loanRepository.findByLoanIdentifier_LoanId(loanId);
        if (loan == null) {
            throw new NotFoundException("Invalid loanId: " + loanId);
        }

        if (loan.getFineIdentifier().getFineId() == null) {
            loan.setFineIdentifier(null);
        }
        return loanResponseMapper.entityToResponseModel(
                loanRepository.findByLoanIdentifier_LoanId(loanId));
    }

    @Override
    public LoanResponseModel addLoanForPatron(String patronId, LoanRequestModel loanRequestModel) {
        if (patronRepository.findByPatronIdentifier_PatronId(patronId) == null) {
            throw new NotFoundException("Invalid patronId: " + patronId);
        }
        Loan loan = loanRequestMapper.requestModelToEntity(loanRequestModel,
                new LoanIdentifier(),
                new PatronIdentifier(patronId),
                new FineIdentifier(patronId));
        return loanResponseMapper.entityToResponseModel(loanRepository.save(loan));
    }

    @Override
    public LoanResponseModel updateLoanForPatron(String patronId, LoanRequestModel loanRequestModel, String loanId) {
        if (patronRepository.findByPatronIdentifier_PatronId(patronId) == null) {
            throw new NotFoundException("Invalid patronId: " + patronId);
        }

        Loan loan = loanRepository.findByLoanIdentifier_LoanId(loanId);
        if (loan == null) {
            throw new NotFoundException("Invalid loanId: " + loanId);
        }

        Loan updatedLoan = loanRequestMapper.requestModelToEntity(loanRequestModel,
                loan.getLoanIdentifier(), new PatronIdentifier(patronId),
                new FineIdentifier(patronId));
        updatedLoan.setId(loan.getId());
        return loanResponseMapper.entityToResponseModel(loanRepository.save(updatedLoan));
    }

    @Override
    public void deleteLoanForPatron(String patronId, String loanId) {
        if (patronRepository.findByPatronIdentifier_PatronId(patronId) == null) {
            throw new NotFoundException("Invalid patronId: " + patronId);
        }
        Loan loan = loanRepository.findByLoanIdentifier_LoanId(loanId);
        if (loan == null) {
            throw new NotFoundException("Invalid loanId: " + loanId);
        }
        loanRepository.delete(loan);
    }
}
