package org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="catalogues")
@Data
@NoArgsConstructor
public class Catalogue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private CatalogueIdentifier catalogueIdentifier;
    private String type;
}
