package org.nneji.libraryws.cataloguesubdomain.presentationlayer.catalogue;

import org.nneji.libraryws.cataloguesubdomain.businesslayer.catalogue.CatalogueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/catalogues")
public class CatalogueController {

    private final CatalogueService catalogueService;

    public CatalogueController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CatalogueResponseModel>> getAllCatalogues() {
        return ResponseEntity.ok().body(catalogueService.getAllCatalogues());
    }

    @GetMapping(value = "{catalogueId}", produces = "application/json")
    public ResponseEntity<CatalogueResponseModel> getCatalogue(@PathVariable String catalogueId) {
        return ResponseEntity.ok().body(catalogueService.getCatalogue(catalogueId));
    }

    public void getCatalogueByISBN(String catalogueId, String isbn) {}
}
