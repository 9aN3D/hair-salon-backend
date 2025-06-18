package pl.edu.wit.hairsalon.user.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.time.LocalDateTime;

public record UserDto(
        String id,
        FullNameDto fullName,
        UserContactDto contact,
        LocalDateTime registrationDateTime
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private FullNameDto fullName;
        private UserContactDto contact;
        private LocalDateTime registrationDateTime;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(FullNameDto fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder contact(UserContactDto contact) {
            this.contact = contact;
            return this;
        }

        public Builder registrationDateTime(LocalDateTime registrationDateTime) {
            this.registrationDateTime = registrationDateTime;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, fullName, contact, registrationDateTime);
        }
    }
}

