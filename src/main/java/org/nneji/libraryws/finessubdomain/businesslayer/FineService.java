package org.nneji.libraryws.finessubdomain.businesslayer;

import org.nneji.libraryws.finessubdomain.presentationlayer.FineRequestModel;
import org.nneji.libraryws.finessubdomain.presentationlayer.FineResponseModel;

import java.util.List;

public interface FineService {
    List<FineResponseModel> getAllFines();

    FineResponseModel getFine(String fineId);

    FineResponseModel addFine(FineRequestModel fineRequestModel);

    FineResponseModel updateFine(FineRequestModel fineRequestModel, String fineId);

    void deleteFine(String fineId);
}
