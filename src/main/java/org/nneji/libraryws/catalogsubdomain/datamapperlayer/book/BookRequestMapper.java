package org.nneji.libraryws.catalogsubdomain.datamapperlayer.book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.Book;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.ISBN;
import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.CatalogIdentifier;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookRequestModel;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(expression = "java(catalogIdentifier)", target = "catalogIdentifier")
    Book requestModelToEntity(BookRequestModel requestModel,
                              ISBN isbn/*, Author author*/, CatalogIdentifier catalogIdentifier);
}
