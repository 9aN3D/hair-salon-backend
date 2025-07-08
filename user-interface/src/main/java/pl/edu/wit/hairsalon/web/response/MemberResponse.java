package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Reprezentuje odpowiedź zawierającą dane użytkownika (członka) systemu.
 *
 * @param id                   identyfikator użytkownika (wymagany, niepusty)
 * @param fullName             imię i nazwisko użytkownika
 * @param contact              dane kontaktowe (np. email, telefon)
 * @param registrationDateTime data i czas rejestracji użytkownika w systemie
 *
 * @see FullNameDto
 * @see MemberContactDto
 */
public record MemberResponse(
        @NotBlank String id,
        @NotBlank FullNameDto fullName,
        @NotBlank MemberContactDto contact,
        @NotNull LocalDateTime registrationDateTime
) {

    /**
     * Porównuje użytkowników po identyfikatorze.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MemberResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    /**
     * HashCode oparty wyłącznie na identyfikatorze.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Tworzy instancję {@link MemberResponse} na podstawie DTO użytkownika.
     *
     * @param dto źródłowy obiekt danych użytkownika
     * @return odpowiedź zawierająca dane użytkownika
     */
    public static MemberResponse of(MemberDto dto) {
        return builder()
                .id(dto.id())
                .fullName(dto.fullName())
                .contact(dto.contact())
                .registrationDateTime(dto.registrationDateTime())
                .build();
    }

    /**
     * Tworzy nowy builder do konstruowania instancji {@link MemberResponse}.
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link MemberResponse}, zapewniający bezpieczną i przejrzystą konstrukcję obiektu.
     */
    public static final class Builder {

        private String id;
        private FullNameDto fullName;
        private MemberContactDto contact;
        private LocalDateTime registrationDateTime;

        /**
         * Ustawia identyfikator użytkownika.
         *
         * @param id identyfikator
         * @return builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Ustawia imię i nazwisko użytkownika.
         *
         * @param fullName imię i nazwisko
         * @return builder
         */
        public Builder fullName(FullNameDto fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * Ustawia dane kontaktowe użytkownika.
         *
         * @param contact kontakt
         * @return builder
         */
        public Builder contact(MemberContactDto contact) {
            this.contact = contact;
            return this;
        }

        /**
         * Ustawia datę i czas rejestracji użytkownika.
         *
         * @param registrationDateTime data rejestracji
         * @return builder
         */
        public Builder registrationDateTime(LocalDateTime registrationDateTime) {
            this.registrationDateTime = registrationDateTime;
            return this;
        }

        /**
         * Buduje nową instancję {@link MemberResponse}, upewniając się, że wszystkie wymagane pola są ustawione.
         *
         * @return obiekt odpowiedzi
         * @throws NullPointerException jeśli któreś z pól jest {@code null}
         */
        public MemberResponse build() {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(fullName, "fullName must not be null");
            Objects.requireNonNull(contact, "contact must not be null");
            Objects.requireNonNull(registrationDateTime, "registrationDateTime must not be null");
            return new MemberResponse(id, fullName, contact, registrationDateTime);
        }

    }

}

