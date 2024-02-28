package org.nneji.libraryws.catalogsubdomain.presentationlayer.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.Author;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestModel {

    private Long isbn;
    private String title;
    private String collection;
    private String edition;
    private String publisher;
    private String synopsis;
    private String language;
    private String status;
    private Author author;
}
