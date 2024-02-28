package org.nneji.libraryws.catalogsubdomain.datamapperlayer.catalog;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.Catalog;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.catalog.CatalogResponseModel;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.catalogbooks.CatalogBooksController;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface CatalogResponseMapper {

    @Mapping(expression = "java(catalog.getCatalogIdentifier().getCatalogId())", target = "catalogId")
    CatalogResponseModel entityToResponseModel(Catalog catalog);

    List<CatalogResponseModel> entityListToResponseModelList(List<Catalog> catalogs);

    @AfterMapping
    default void addLinks(@MappingTarget CatalogResponseModel model, Catalog catalog) {
        //self link
        Link selfLink = linkTo(methodOn(CatalogBooksController.class)
                .getCatalog(model.getCatalogId()))
                .withSelfRel();
        model.add(selfLink);

        //all catalogues
        Link cataloguesLink = linkTo(methodOn(CatalogBooksController.class)
                .getAllCatalogs())
                .withRel("catalogs");
        model.add(cataloguesLink);
    }
}
