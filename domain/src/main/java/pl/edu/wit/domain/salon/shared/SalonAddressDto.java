package pl.edu.wit.domain.salon.shared;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalonAddressDto {

    private String streetName;

    private String postalCode;

    private String city;

    private String country;

}
