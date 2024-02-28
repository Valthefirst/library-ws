package org.nneji.libraryws.catalogsubdomain.datamapperlayer.catalog;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.Catalog;
import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.CatalogIdentifier;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.catalog.CatalogRequestModel;

@Mapper(componentModel = "spring")
public interface CatalogueRequestMapper {

    @Mapping(target = "id", ignore = true)
    Catalog requestModelToEntity(CatalogRequestModel requestModel, CatalogIdentifier catalogIdentifier);
}
