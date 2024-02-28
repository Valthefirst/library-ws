package org.nneji.libraryws.catalogsubdomain.presentationlayer.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.Author;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.Status;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseModel extends RepresentationModel<BookResponseModel> {

    private Long isbn;
    private String catalogId;
    private String title;
    private String collection;
    private String edition;
    private String publisher;
    private String synopsis;
    private String language;
    private Status status;
    private Author author;
}
