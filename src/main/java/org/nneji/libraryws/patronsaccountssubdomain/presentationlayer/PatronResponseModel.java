package org.nneji.libraryws.patronsaccountssubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.ContactMethodPreference;
import org.nneji.libraryws.patronsaccountssubdomain.datalayer.PhoneNumber;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatronResponseModel extends RepresentationModel<PatronResponseModel> {

    private String patronId;
    private String firstName;
    private String lastName;
    private String emailAddress;

    private ContactMethodPreference contactMethodPreference;

    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private List<PhoneNumber> phoneNumbers;
}
