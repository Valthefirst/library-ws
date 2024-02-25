package org.nneji.libraryws.accountssubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nneji.libraryws.accountssubdomain.datalayer.PhoneNumber;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatronRequestModel {

    private String firstName;
    private String lastName;
    private String emailAddress;

    private String contactMethodPreference;

    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private List<PhoneNumber> phoneNumbers;
}
