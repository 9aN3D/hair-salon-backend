package pl.edu.wit.hairsalon.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.user.dto.UserFullNameDto;

@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
class UserFullName implements SelfValidator<UserFullName> {

    private final String name;
    private final String surname;

    @Override
    public UserFullName validate() {
        validate(new NotBlankString(name), new NotBlankString(surname));
        return this;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    UserFullNameDto toDto() {
        return UserFullNameDto.builder()
                .name(name)
                .surname(surname)
                .build();
    }

}
