package org.nneji.libraryws.loanssubdomain.presentationlayer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.ISBN;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseModel extends RepresentationModel<LoanResponseModel> {

    private String loanId;
    private String patronId;
    private String fineId;
    private LoanStatus status;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private List<ISBN> books;
}
