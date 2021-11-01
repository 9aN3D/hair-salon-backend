package pl.edu.wit.hairsalon.member.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberFullNameDto {

    String name;

    String surname;

    public String getFullName() {
        return name + " " + surname;
    }

}
