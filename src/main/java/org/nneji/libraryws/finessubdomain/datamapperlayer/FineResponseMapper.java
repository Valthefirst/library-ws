package org.nneji.libraryws.finessubdomain.datamapperlayer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.nneji.libraryws.finessubdomain.datalayer.Fine;
import org.nneji.libraryws.finessubdomain.presentationlayer.FineController;
import org.nneji.libraryws.finessubdomain.presentationlayer.FineResponseModel;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.Patron;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface FineResponseMapper {

    @Mapping(expression = "java(fine.getFineIdentifier() != null ? fine.getFineIdentifier().getFineId() : null)",
            target = "fineId")
//    @Mapping(expression = "java(patron.getPatronIdentifier().getPatronId())", target = "patronId")
    FineResponseModel entityToResponseModel(Fine fine);

    List<FineResponseModel> entityListToResponseModelList(List<Fine> fines);

    @AfterMapping
    default void addLinks(@MappingTarget FineResponseModel model, Fine fine) {
        //self link
        Link selfLink = linkTo(methodOn(FineController.class)
                .getFine(model.getFineId()))
                .withSelfRel();
        model.add(selfLink);

        // all patrons link
        Link finesLink = linkTo(methodOn(FineController.class)
                .getAllFines())
                .withRel("fines");
        model.add(finesLink);
    }
}
