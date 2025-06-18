package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.time.LocalDateTime;
import java.util.Objects;

public record MemberResponse(
        @NotBlank String id,
        @NotBlank FullNameDto fullName,
        @NotBlank MemberContactDto contact,
        @NotNull LocalDateTime registrationDateTime
) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MemberResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static MemberResponse of(MemberDto dto) {
        return builder()
                .id(dto.id())
                .fullName(dto.fullName())
                .contact(dto.contact())
                .registrationDateTime(dto.registrationDateTime())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private FullNameDto fullName;
        private MemberContactDto contact;
        private LocalDateTime registrationDateTime;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(FullNameDto fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder contact(MemberContactDto contact) {
            this.contact = contact;
            return this;
        }

        public Builder registrationDateTime(LocalDateTime registrationDateTime) {
            this.registrationDateTime = registrationDateTime;
            return this;
        }

        public MemberResponse build() {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(fullName, "fullName must not be null");
            Objects.requireNonNull(contact, "contact must not be null");
            Objects.requireNonNull(registrationDateTime, "registrationDateTime must not be null");
            return new MemberResponse(id, fullName, contact, registrationDateTime);
        }

    }

}

