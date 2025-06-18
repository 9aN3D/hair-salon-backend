package pl.edu.wit.hairsalon.sharedKernel.domain;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

public record FullName(String name, String surname) implements SelfValidator<FullName> {

    public FullName(FullNameDto dto) {
        this(dto.name(), dto.surname());
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
