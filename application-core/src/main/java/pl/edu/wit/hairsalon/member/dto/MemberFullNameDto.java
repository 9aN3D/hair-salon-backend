package pl.edu.wit.hairsalon.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class MemberFullNameDto {

    String name;

    String surname;

    public String getFullName() {
        return name + " " + surname;
    }

}
