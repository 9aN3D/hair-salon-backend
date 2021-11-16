package pl.edu.wit.hairsalon.hairdresser;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserFullNameDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class HairdresserFullName implements SelfValidator<HairdresserFullName> {

    private final String name;
    private final String surname;

    public HairdresserFullName(HairdresserFullNameDto dto) {
        this(dto.getName(), dto.getSurname());
    }

    @Override
    public HairdresserFullName validate() {
        validate(new NotBlankString(name), new NotBlankString(surname));
        return this;
    }

    HairdresserFullNameDto toDto() {
        return HairdresserFullNameDto.builder()
                .name(name)
                .surname(surname)
                .build();
    }

}
