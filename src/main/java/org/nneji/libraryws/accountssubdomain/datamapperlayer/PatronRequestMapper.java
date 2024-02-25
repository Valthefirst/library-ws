package org.nneji.libraryws.accountssubdomain.datamapperlayer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nneji.libraryws.accountssubdomain.datalayer.Address;
import org.nneji.libraryws.accountssubdomain.datalayer.Patron;
import org.nneji.libraryws.accountssubdomain.datalayer.PatronIdentifier;
import org.nneji.libraryws.accountssubdomain.presentationlayer.PatronRequestModel;

@Mapper(componentModel = "spring")
public interface PatronRequestMapper {

    @Mapping(target = "id", ignore = true)
    Patron requestModelToEntity(PatronRequestModel patronRequestModel,
                                PatronIdentifier patronIdentifier,
                                Address address);
}
