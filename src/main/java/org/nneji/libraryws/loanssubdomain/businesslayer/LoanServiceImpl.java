package org.nneji.libraryws.loanssubdomain.businesslayer;

import org.nneji.libraryws.catalogsubdomain.datalayer.books.Book;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.BookRepository;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.Status;
import org.nneji.libraryws.finessubdomain.datalayer.Fine;
import org.nneji.libraryws.finessubdomain.datalayer.FineIdentifier;
import org.nneji.libraryws.finessubdomain.datalayer.FineRepository;
import org.nneji.libraryws.loanssubdomain.datalayer.Loan;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanIdentifier;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanRepository;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanStatus;
import org.nneji.libraryws.loanssubdomain.datamapperlayer.LoanRequestMapper;
import org.nneji.libraryws.loanssubdomain.datamapperlayer.LoanResponseMapper;
import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanRequestModel;
import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanResponseModel;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronIdentifier;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronRepository;
import org.nneji.libraryws.utils.exceptions.NotFoundException;
import org.nneji.libraryws.utils.exceptions.UnavailableBookException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;
    private final FineRepository fineRepository;
    private final LoanResponseMapper loanResponseMapper;
    private final LoanRequestMapper loanRequestMapper;

    public LoanServiceImpl(LoanRepository loanRepository, PatronRepository patronRepository, BookRepository bookRepository, FineRepository fineRepository, LoanResponseMapper loanResponseMapper, LoanRequestMapper loanRequestMapper) {
        this.loanRepository = loanRepository;
        this.patronRepository = patronRepository;
        this.bookRepository = bookRepository;
        this.fineRepository = fineRepository;
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

        if (loan.getFineIdentifier() == null) {
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

        loanRequestModel.getBooks().forEach(book -> {
            Long isbn = book.getIsbn();
            Book bookObject = bookRepository.findByIsbn_Isbn(isbn);

            if (bookObject == null) {
                throw new NotFoundException("Invalid ISBN: " + isbn);
            }

            switch (bookObject.getStatus()) {
                case BORROWED:
                    throw new UnavailableBookException("Book with ISBN: " + isbn + " is already borrowed");
                case LOST:
                    throw new UnavailableBookException("Book with ISBN: " + isbn + " is lost");
                case DAMAGED:
                    throw new UnavailableBookException("Book with ISBN: " + isbn + " is damaged");
            }

            bookObject.setStatus(Status.BORROWED);
        });

        Loan loan = loanRequestMapper.requestModelToEntity(loanRequestModel,
                new LoanIdentifier(),
                new PatronIdentifier(patronId), null);
        loan.setBorrowedDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(21));
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

        Loan updatedLoan;
        if (loanRequestModel.getFineId() == null) {
            updatedLoan = loanRequestMapper.requestModelToEntity(loanRequestModel,
                    loan.getLoanIdentifier(), new PatronIdentifier(patronId), null);
        }
        else
        {
            updatedLoan = loanRequestMapper.requestModelToEntity(loanRequestModel,
                    loan.getLoanIdentifier(), new PatronIdentifier(patronId), new FineIdentifier(loanRequestModel.getFineId()));
        }
        updatedLoan.setId(loan.getId());
        updatedLoan.setBorrowedDate(loan.getBorrowedDate());
        if (loanRequestModel.getDueDate() == null)
            updatedLoan.setDueDate(loan.getDueDate());
        else
            updatedLoan.setDueDate(loanRequestModel.getDueDate());
        updatedLoan.setReturnedDate(loan.getReturnedDate());

        LoanStatus previousStatus = loan.getStatus();
        if (previousStatus != LoanStatus.RETURNED && loanRequestModel.getStatus() == LoanStatus.RETURNED) {
            updatedLoan.getBooks().forEach(book -> {
                Long isbn = book.getIsbn();
                Book bookObject = bookRepository.findByIsbn_Isbn(isbn);
                bookObject.setStatus(Status.AVAILABLE);
            });
            if (loan.getReturnedDate() == null) {
                updatedLoan.setReturnedDate(LocalDate.now());
            }
            long days = ChronoUnit.DAYS.between(loan.getDueDate(), updatedLoan.getReturnedDate());
            int numBooks = updatedLoan.getBooks().size();
            if (days > 0) {
                Fine lateFine = new Fine(BigDecimal.valueOf(0.25 * days * numBooks), "Late return", false);
                updatedLoan.setFineIdentifier(lateFine.getFineIdentifier());
                fineRepository.save(lateFine);
            }
        }
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
