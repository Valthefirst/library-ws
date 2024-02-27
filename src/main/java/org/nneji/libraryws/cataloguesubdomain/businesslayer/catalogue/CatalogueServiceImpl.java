package org.nneji.libraryws.cataloguesubdomain.businesslayer.catalogue;

import org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue.Catalogue;
import org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue.CatalogueRepository;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.catalogue.CatalogueResponseModel;
import org.nneji.libraryws.cataloguesubdomain.datamapperlayer.catalogues.CatalogueResponseMapper;
import org.nneji.libraryws.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogueServiceImpl implements CatalogueService{

    private final CatalogueRepository catalogueRepository;
    private final CatalogueResponseMapper catalogueResponseMapper;

    public CatalogueServiceImpl(CatalogueRepository catalogueRepository, CatalogueResponseMapper catalogueResponseMapper) {
        this.catalogueRepository = catalogueRepository;
        this.catalogueResponseMapper = catalogueResponseMapper;
    }

    @Override
    public List<CatalogueResponseModel> getAllCatalogues() {
        List<Catalogue> catalogues = catalogueRepository.findAll();
        return catalogueResponseMapper.entityListToResponseModelList(catalogues);
    }

    @Override
    public CatalogueResponseModel getCatalogue(String catalogueId) {
        Catalogue catalogue = catalogueRepository.findCatalogueByCatalogueIdentifier_CatalogueId(catalogueId);
        if (catalogue == null)
            throw new NotFoundException("Unknown catalogueId provided: " + catalogueId);
        return catalogueResponseMapper.entityToResponseModel(catalogue);
    }
}
