package pl.edu.wit.domain.salon.shared;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.domain.cqrs.Command;

@Value
@Builder
public class CreateSalonCommand implements Command<CreateSalonResult> {

    String name;
    Address address;

    @Value
    @Builder
    public static class Address {

        String streetName;
        String postalCode;
        String city;
        String country;

    }

}
