package org.nneji.libraryws.patronsaccountssubdomain.datamapperlayer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.Address;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.Patron;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronIdentifier;
import org.nneji.libraryws.patronsaccountssubdomain.presentationlayer.PatronRequestModel;

@Mapper(componentModel = "spring")
public interface PatronRequestMapper {

    @Mapping(target = "id", ignore = true)
    Patron requestModelToEntity(PatronRequestModel patronRequestModel,
                                PatronIdentifier patronIdentifier,
                                Address address);
}
