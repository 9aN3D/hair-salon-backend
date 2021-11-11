package pl.edu.wit.hairsalon.sharedkernel.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class FullNameDto {

    private final String name;
    private final String surname;

    @Override
    public String toString() {
        return name + ' ' + surname;
    }

}
