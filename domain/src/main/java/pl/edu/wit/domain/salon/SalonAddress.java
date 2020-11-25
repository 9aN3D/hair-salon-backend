package pl.edu.wit.domain.salon;

import pl.edu.wit.domain.common.StringNotBlank;
import pl.edu.wit.domain.salon.shared.CreateSalonCommand;
import pl.edu.wit.domain.salon.shared.SalonAddressDto;

class SalonAddress {

    private final String streetName;

    private final String postalCode;

    private final String city;

    private final String country;

    public SalonAddress(String streetName, String postalCode, String city, String country) {
        this.streetName = new StringNotBlank(streetName).validate();
        this.postalCode = new StringNotBlank(postalCode).validate();
        this.city = new StringNotBlank(city).validate();
        this.country = new StringNotBlank(country).validate();
    }

    public SalonAddress(CreateSalonCommand.Address address) {
        this(address.getStreetName(), address.getPostalCode(), address.getCity(), address.getCountry());
    }

    public SalonAddressDto toDto() {
        return SalonAddressDto.builder()
                .streetName(streetName)
                .postalCode(postalCode)
                .city(city)
                .country(country)
                .build();
    }

}
