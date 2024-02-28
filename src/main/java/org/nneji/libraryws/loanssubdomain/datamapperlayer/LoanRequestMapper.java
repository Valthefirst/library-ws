package org.nneji.libraryws.loanssubdomain.datamapperlayer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nneji.libraryws.finessubdomain.datalayer.FineIdentifier;
import org.nneji.libraryws.loanssubdomain.datalayer.Loan;
import org.nneji.libraryws.loanssubdomain.datalayer.LoanIdentifier;
import org.nneji.libraryws.loanssubdomain.presentationlayer.LoanRequestModel;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronIdentifier;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "loanId", source = "loanId")
//    @Mapping(expression = "java(patronIdentifier)", target = "patronIdentifier")
    Loan requestModelToEntity(LoanRequestModel requestModel, LoanIdentifier loanIdentifier,
                              PatronIdentifier patronIdentifier, FineIdentifier fineIdentifier);
}
