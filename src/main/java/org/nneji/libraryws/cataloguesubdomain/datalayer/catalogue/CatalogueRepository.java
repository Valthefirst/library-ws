package org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogueRepository extends JpaRepository<Catalogue, Integer>{

    Catalogue findCatalogueByCatalogueIdentifier_CatalogueId(String catalogueId);
}
