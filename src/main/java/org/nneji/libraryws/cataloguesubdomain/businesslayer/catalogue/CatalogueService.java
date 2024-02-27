package org.nneji.libraryws.cataloguesubdomain.businesslayer.catalogue;

import org.nneji.libraryws.cataloguesubdomain.presentationlayer.catalogue.CatalogueResponseModel;

import java.util.List;

public interface CatalogueService {

    List<CatalogueResponseModel> getAllCatalogues();
    CatalogueResponseModel getCatalogue(String catalogueId);
}
