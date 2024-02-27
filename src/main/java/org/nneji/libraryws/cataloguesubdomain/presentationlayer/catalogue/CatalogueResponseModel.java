package org.nneji.libraryws.cataloguesubdomain.presentationlayer.catalogue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueResponseModel extends RepresentationModel<CatalogueResponseModel> {

    private String catalogueId;
    private String type;
}
