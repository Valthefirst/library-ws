package org.nneji.libraryws.finessubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineResponseModel extends RepresentationModel<FineResponseModel> {

    private String fineId;
    private Double amount;
    private String reason;
    private Boolean isPaid;
//    private String patronId;
}
