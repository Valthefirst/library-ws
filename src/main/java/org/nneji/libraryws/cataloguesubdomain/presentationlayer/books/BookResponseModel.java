package org.nneji.libraryws.cataloguesubdomain.presentationlayer.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.Author;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.ISBN;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseModel extends RepresentationModel<BookResponseModel> {

    private String isbn;
    private String catalogueId;
    private String title;
    private String collection;
    private String edition;
    private String publisher;
    private String synopsis;
//    private String authorFirstName;
//    private String authorLastName;
    private Author author;
}
