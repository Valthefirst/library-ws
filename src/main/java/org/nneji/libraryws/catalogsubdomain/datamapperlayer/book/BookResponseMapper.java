package org.nneji.libraryws.catalogsubdomain.datamapperlayer.book;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.Book;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookResponseModel;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.catalogbooks.CatalogBooksController;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {

    @Mapping(expression = "java(book.getIsbn().getIsbn())", target = "isbn")
    @Mapping(expression = "java(book.getCatalogIdentifier().getCatalogId())", target = "catalogId")
    BookResponseModel entityToResponseModel(Book book);

    List<BookResponseModel> entityListToResponseModelList(List<Book> books);

    @AfterMapping
    default void addLinks(@MappingTarget BookResponseModel model, Book book) {
        //self link
        Link selfLink = linkTo(methodOn(CatalogBooksController.class)
                .getBook(model.getCatalogId(), model.getIsbn()))
                .withSelfRel();
        model.add(selfLink);

        // all books link
        Link booksLink = linkTo(methodOn(CatalogBooksController.class)
                .getAllBooks(model.getCatalogId()))
                .withRel("books in this catalog");
        model.add(booksLink);

        //get catalogue by id
        Link catalogueId = linkTo(methodOn(CatalogBooksController.class)
                .getCatalog(model.getCatalogId()))
                .withRel("catalog");
        model.add(catalogueId);
    }
}
