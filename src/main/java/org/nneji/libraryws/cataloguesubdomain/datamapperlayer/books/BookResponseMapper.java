package org.nneji.libraryws.cataloguesubdomain.datamapperlayer.books;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.Book;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.books.BookController;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.books.BookResponseModel;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.catalogue.CatalogueController;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.Patron;
import org.nneji.libraryws.patronsaccountssubdomain.presentationlayer.PatronController;
import org.nneji.libraryws.patronsaccountssubdomain.presentationlayer.PatronResponseModel;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {

    @Mapping(expression = "java(book.getIsbn().getIsbn())", target = "isbn")
    @Mapping(expression = "java(book.getCatalogueIdentifier().getCatalogueId())", target = "catalogueId")
//    @Mapping(expression = "java(book.getAuthor().getFirstName())", target = "authorFirstName")
//    @Mapping(expression = "java(book.getAuthor().getLastName())", target = "authorLastName")
    BookResponseModel entityToResponseModel(Book book);

    List<BookResponseModel> entityListToResponseModelList(List<Book> books);

    @AfterMapping
    default void addLinks(@MappingTarget BookResponseModel model, Book book) {
        //self link
        Link selfLink = linkTo(methodOn(BookController.class)
                .getBook(model.getIsbn()))
                .withSelfRel();
        model.add(selfLink);

        // all books link
        Link booksLink = linkTo(methodOn(BookController.class)
                .getAllBooks())
                .withRel("books");
        model.add(booksLink);

        //get catalogue by id
        Link catalogueId = linkTo(methodOn(CatalogueController.class)
                .getCatalogue(model.getCatalogueId()))
                .withRel("catalogue");
        model.add(catalogueId);
    }
}
