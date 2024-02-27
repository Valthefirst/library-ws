package org.nneji.libraryws.cataloguesubdomain.datamapperlayer.catalogues;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue.Catalogue;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.catalogue.CatalogueController;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.catalogue.CatalogueResponseModel;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface CatalogueResponseMapper {

    @Mapping(expression = "java(catalogue.getCatalogueIdentifier().getCatalogueId())", target = "catalogueId")
    CatalogueResponseModel entityToResponseModel(Catalogue catalogue);

    List<CatalogueResponseModel> entityListToResponseModelList(List<Catalogue> catalogues);

    @AfterMapping
    default void addLinks(@MappingTarget CatalogueResponseModel model, Catalogue catalogue) {
        //self link
        Link selfLink = linkTo(methodOn(CatalogueController.class)
                .getCatalogue(model.getCatalogueId()))
                .withSelfRel();
        model.add(selfLink);

        //all catalogues
        Link cataloguesLink = linkTo(methodOn(CatalogueController.class)
                .getAllCatalogues())
                .withRel("allCatalogues");
        model.add(cataloguesLink);
    }
}
