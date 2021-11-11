package pl.edu.wit.hairsalon.hairdresser.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.sharedkernel.dto.FullNameDto;

@Value
@EqualsAndHashCode(callSuper = true)
public class HairdresserFullNameDto extends FullNameDto {

    @Builder
    public HairdresserFullNameDto(String name, String surname) {
        super(name, surname);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
