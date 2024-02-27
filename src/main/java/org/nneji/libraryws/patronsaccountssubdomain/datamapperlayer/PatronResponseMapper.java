package org.nneji.libraryws.patronsaccountssubdomain.datamapperlayer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.Patron;
import org.nneji.libraryws.patronsaccountssubdomain.presentationlayer.PatronController;
import org.nneji.libraryws.patronsaccountssubdomain.presentationlayer.PatronResponseModel;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface PatronResponseMapper {

    @Mapping(expression = "java(patron.getPatronIdentifier().getPatronId())", target = "patronId")
    @Mapping(expression = "java(patron.getAddress().getStreetAddress())", target = "streetAddress")
    @Mapping(expression = "java(patron.getAddress().getCity())", target = "city")
    @Mapping(expression = "java(patron.getAddress().getProvince())", target = "province")
    @Mapping(expression = "java(patron.getAddress().getCountry())", target = "country")
    @Mapping(expression = "java(patron.getAddress().getPostalCode())", target = "postalCode")
    PatronResponseModel entityToResponseModel(Patron patron);

    List<PatronResponseModel> entityListToResponseModelList(List<Patron> patrons);

    @AfterMapping
    default void addLinks(@MappingTarget PatronResponseModel model, Patron patron) {
        //self link
        Link selfLink = linkTo(methodOn(PatronController.class)
                .getPatron(model.getPatronId()))
                .withSelfRel();
        model.add(selfLink);

        // all patrons link
        Link patronsLink = linkTo(methodOn(PatronController.class)
                .getAllPatrons())
                .withRel("patrons");
        model.add(patronsLink);
    }
}
