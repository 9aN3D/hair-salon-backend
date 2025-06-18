package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;
import pl.edu.wit.hairsalon.user.dto.UserDto;

public record UserResponse(
        @NotBlank String id,
        @NotNull FullNameDto fullName,
        @NotNull UserContactDto contact
) {

    public static UserResponse of(UserDto userDto) {
        return UserResponse.builder()
                .id(userDto.id())
                .fullName(userDto.fullName())
                .contact(userDto.contact())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private FullNameDto fullName;
        private UserContactDto contact;

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

        public UserResponse build() {
            return new UserResponse(id, fullName, contact);
        }

    }

}
