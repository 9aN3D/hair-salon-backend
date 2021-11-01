package pl.edu.wit.hairsalon.member;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.member.dto.MemberFullNameDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;

@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
class MemberFullName implements SelfValidator<MemberFullName> {

    private final String name;
    private final String surname;

    @Override
    public MemberFullName validate() {
        validate(new NotBlankString(name), new NotBlankString(surname));
        return this;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    MemberFullNameDto toDto() {
        return MemberFullNameDto.builder()
                .name(name)
                .surname(surname)
                .build();
    }

}
