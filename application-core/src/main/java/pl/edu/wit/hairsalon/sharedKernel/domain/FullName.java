package pl.edu.wit.hairsalon.sharedKernel.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class FullName implements SelfValidator<FullName> {

    private final String name;
    private final String surname;

    public FullName(FullNameDto dto) {
        this(dto.getName(), dto.getSurname());
    }

    @Override
    public FullName validate() {
        validate(new NotBlankString(name), new NotBlankString(surname));
        return this;
    }

    public FullNameDto toDto() {
        return new FullNameDto(name, surname);
    }

}
