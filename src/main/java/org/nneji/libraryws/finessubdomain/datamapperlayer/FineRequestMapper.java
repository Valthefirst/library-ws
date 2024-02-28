package org.nneji.libraryws.finessubdomain.datamapperlayer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nneji.libraryws.finessubdomain.datalayer.Fine;
import org.nneji.libraryws.finessubdomain.datalayer.FineIdentifier;
import org.nneji.libraryws.finessubdomain.presentationlayer.FineRequestModel;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronIdentifier;

@Mapper(componentModel = "spring")
public interface FineRequestMapper {

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "fineId", source = "fineIdentifier")
//    @Mapping(expression = "java(patronIdentifier)", target = "patronIdentifier")
    Fine requestModelToEntity(FineRequestModel fineRequestModel,
                              FineIdentifier fineIdentifier);
}
