package org.nneji.libraryws.cataloguesubdomain.presentationlayer.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.Author;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestModel {

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
