package pl.edu.wit.hairsalon.user.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFullNameDto {

    String name;

    String surname;

    public String getFullName() {
        return name + " " + surname;
    }

}
