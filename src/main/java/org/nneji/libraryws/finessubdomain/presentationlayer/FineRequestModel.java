package org.nneji.libraryws.finessubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineRequestModel {

    private Double amount;
    private String reason;
    private Boolean isPaid;
    private String patronId;
}
