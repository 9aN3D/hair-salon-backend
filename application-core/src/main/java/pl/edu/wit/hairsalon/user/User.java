package pl.edu.wit.hairsalon.user;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Objects;

record User(
        String id,
        FullName fullName,
        UserContact contact,
        LocalDateTime registrationDateTime
) implements SelfValidator<User> {

    public User validate() {
        Objects.requireNonNull(fullName, "User full name must not be null");
        Objects.requireNonNull(contact, "User contact must not be null");
        Objects.requireNonNull(registrationDateTime, "User registration date time must not be null");
        validate(new NotBlankString(id), fullName, contact);
        return this;
    }

    UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .fullName(fullName.toDto())
                .contact(contact.toDto())
                .registrationDateTime(registrationDateTime)
                .build();
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private FullName fullName;
        private UserContact contact;
        private LocalDateTime registrationDateTime;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder fullName(FullName fullName) {
            this.fullName = fullName;
            return this;
        }

        Builder contact(UserContact contact) {
            this.contact = contact;
            return this;
        }

        Builder registrationDateTime(LocalDateTime registrationDateTime) {
            this.registrationDateTime = registrationDateTime;
            return this;
        }

        User build() {
            return new User(id, fullName, contact, registrationDateTime);
        }

    }

}
