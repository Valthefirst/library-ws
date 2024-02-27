package org.nneji.libraryws.cataloguesubdomain.datamapperlayer.books;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.Author;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.Book;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.ISBN;
import org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue.CatalogueIdentifier;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.books.BookRequestModel;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(expression = "java(catalogueIdentifier)", target = "catalogueIdentifier")
    Book requestModelToEntity(BookRequestModel requestModel,
                              ISBN isbn/*, Author author*/, CatalogueIdentifier catalogueIdentifier);
}
