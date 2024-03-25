package org.nneji.libraryws.finessubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineRequestModel {

    private BigDecimal amount;
    private String reason;
    private Boolean isPaid;
    private String patronId;
}
