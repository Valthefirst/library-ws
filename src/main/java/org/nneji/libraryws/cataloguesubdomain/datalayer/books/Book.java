package org.nneji.libraryws.cataloguesubdomain.datalayer.books;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue.CatalogueIdentifier;

@Entity
@Table(name="books")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private ISBN isbn;

    @Embedded
    private CatalogueIdentifier catalogueIdentifier;
    private String title;
    private String collection;
    private String edition;
    private String publisher;
    private String synopsis;

    @Embedded
    private Author author;

    public Book(@NotNull ISBN isbn, @NotNull CatalogueIdentifier catalogueIdentifier, @NotNull String title, String collection, @NotNull String edition, @NotNull String publisher, @NotNull String synopsis, @NotNull Author author) {
        this.isbn = isbn;
        this.catalogueIdentifier = catalogueIdentifier;
        this.title = title;
        this.collection = collection;
        this.edition = edition;
        this.publisher = publisher;
        this.synopsis = synopsis;
        this.author = author;
    }

}
