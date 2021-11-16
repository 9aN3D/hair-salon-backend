package pl.edu.wit.hairsalon.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class User implements SelfValidator<User> {

    private final String id;
    private final UserFullName fullName;
    private final UserContact contact;
    private final LocalDateTime registrationDateTime;

    public User validate() {
        Objects.requireNonNull(fullName, "User full name must not be null");
        Objects.requireNonNull(contact, "User contact must not be null");
        Objects.requireNonNull(registrationDateTime, "User registration date time must not be null");
        validate(new NotBlankString(id), fullName, contact);
        return this;
    }

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .fullName(fullName.toDto())
                .contact(contact.toDto())
                .registrationDateTime(registrationDateTime)
                .build();
    }

}
