package org.nneji.libraryws.patronsaccountssubdomain.businesslayer;

import org.nneji.libraryws.patronsaccountssubdomain.presentationlayer.PatronRequestModel;
import org.nneji.libraryws.patronsaccountssubdomain.presentationlayer.PatronResponseModel;

import java.util.List;

public interface PatronService {

    List<PatronResponseModel> getAllPatrons();
    PatronResponseModel getPatron(String patronId);
    PatronResponseModel addPatron(PatronRequestModel patronRequestModel);
    PatronResponseModel updatePatron(PatronRequestModel patronRequestModel, String patronId);
    void removePatron(String patronId);
}
