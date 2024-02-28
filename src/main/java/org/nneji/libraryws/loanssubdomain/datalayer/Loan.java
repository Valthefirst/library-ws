package org.nneji.libraryws.loanssubdomain.datalayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.ISBN;
import org.nneji.libraryws.finessubdomain.datalayer.FineIdentifier;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronIdentifier;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private LoanIdentifier loanIdentifier;

    @Embedded
    private PatronIdentifier patronIdentifier;

    @Embedded
    private FineIdentifier fineIdentifier;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "loan_books", joinColumns = @JoinColumn(name="loan_id"))
    private List<ISBN> books;

    public Loan(@NotNull PatronIdentifier patronIdentifier, FineIdentifier fineIdentifier,
                @NotNull LoanIdentifier loanIdentifier, @NotNull LoanStatus status, @NotNull LocalDate borrowedDate,
                @NotNull LocalDate dueDate, LocalDate returnedDate, @NotNull List<ISBN> books) {
        this.patronIdentifier = patronIdentifier;
        this.fineIdentifier = fineIdentifier;
        this.loanIdentifier = loanIdentifier;
        this.status = status;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.books = books;
    }
}
