package org.nneji.libraryws.loanssubdomain.datamapperlayer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.Book;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookResponseModel;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.catalogbooks.CatalogBooksController;
import org.nneji.libraryws.loanssubdomain.datalayer.Loan;
import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanResponseModel;
import org.nneji.libraryws.loanssubdomain.presentationlayer.PatronLoansController;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface LoanResponseMapper {

    @Mapping(expression = "java(loan.getLoanIdentifier().getLoanId())", target = "loanId")
    @Mapping(expression = "java(loan.getPatronIdentifier().getPatronId())", target = "patronId")
    @Mapping(expression = "java(loan.getFineIdentifier().getFineId())", target = "fineId")
    LoanResponseModel entityToResponseModel(Loan loan);

    List<LoanResponseModel> entityListToResponseModelList(List<Loan> books);

    @AfterMapping
    default void addLinks(@MappingTarget LoanResponseModel model, Loan loan) {
        //self link
        Link selfLink = linkTo(methodOn(PatronLoansController.class)
                .getLoanForPatron(model.getPatronId(), model.getLoanId()))
                .withSelfRel();
        model.add(selfLink);

        // all loans link
        Link booksLink = linkTo(methodOn(PatronLoansController.class)
                .getAllLoansForPatron(model.getPatronId()))
                .withRel("loans for this patron");
        model.add(booksLink);
//
//        //get patron by id
//        Link catalogueId = linkTo(methodOn(PatronLoansController.class)
//                .getLoanForPatron(model.getCatalogId()))
//                .withRel("catalog");
//        model.add(catalogueId);
    }
}
