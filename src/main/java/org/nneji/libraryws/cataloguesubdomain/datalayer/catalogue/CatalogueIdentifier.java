package org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
@AllArgsConstructor
public class CatalogueIdentifier {

    private String catalogueId;

    public CatalogueIdentifier() {
        this.catalogueId = UUID.randomUUID().toString();
    }
}
