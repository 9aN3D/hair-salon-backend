package pl.edu.wit.hairsalon.hairdresser.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HairdresserFullNameDto {

    String name;

    String surname;

    @Override
    public String toString() {
        return name + ' ' + surname;
    }

}
