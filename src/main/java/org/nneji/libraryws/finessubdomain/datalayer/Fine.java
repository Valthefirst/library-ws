package org.nneji.libraryws.finessubdomain.datalayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PatronIdentifier;

@Entity
@Table(name = "fines")
@Data
@NoArgsConstructor
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private FineIdentifier fineIdentifier;
    private Double amount;
    private String reason;
    private Boolean isPaid;

    public Fine(@NotNull Double amount, @NotNull String reason, @NotNull Boolean isPaid) {
        this.fineIdentifier = new FineIdentifier();
        this.amount = amount;
        this.reason = reason;
        this.isPaid = isPaid;;
    }
}
