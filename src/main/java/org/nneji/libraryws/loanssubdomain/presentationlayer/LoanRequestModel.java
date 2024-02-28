package org.nneji.libraryws.loanssubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.ISBN;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestModel {

    private String fineId;
    private LoanStatus status;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private List<ISBN> books;
}
