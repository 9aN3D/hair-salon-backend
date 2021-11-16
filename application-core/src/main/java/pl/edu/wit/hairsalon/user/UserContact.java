package pl.edu.wit.hairsalon.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;

@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
class UserContact implements SelfValidator<UserContact> {

    private final String email;

    @Override
    public UserContact validate() {
        validate(new NotBlankString(email));
        return this;
    }

    UserContactDto toDto() {
        return UserContactDto.builder()
                .email(email)
                .build();
    }

}
