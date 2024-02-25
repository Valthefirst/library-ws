package org.nneji.libraryws.accountssubdomain.businesslayer;

import org.nneji.libraryws.accountssubdomain.presentationlayer.PatronRequestModel;
import org.nneji.libraryws.accountssubdomain.presentationlayer.PatronResponseModel;

import java.util.List;

public interface PatronService {

    List<PatronResponseModel> getPatrons();
    PatronResponseModel getPatronById(String patronId);
    PatronResponseModel addPatron(PatronRequestModel patronRequestModel);
    PatronResponseModel updatePatron(PatronRequestModel patronRequestModel, String patronId);
    void removePatron(String patronId);
}
