package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;
import pl.edu.wit.hairsalon.user.dto.UserDto;

/**
 * Odpowiedź reprezentująca podstawowe dane użytkownika systemu (np. administratora, role: ADMIN).
 * <p>
 * Zawiera identyfikator, imię i nazwisko oraz dane kontaktowe użytkownika.
 * </p>
 *
 * @param id        identyfikator użytkownika
 * @param fullName  imię i nazwisko użytkownika
 * @param contact   dane kontaktowe użytkownika (np. e-mail, telefon)
 *
 * @see UserDto
 * @see FullNameDto
 * @see UserContactDto
 */
public record UserResponse(
        @NotBlank String id,
        @NotNull FullNameDto fullName,
        @NotNull UserContactDto contact
) {

    /**
     * Tworzy instancję {@link UserResponse} na podstawie DTO użytkownika.
     *
     * @param userDto obiekt źródłowy z danymi użytkownika
     * @return nowa instancja odpowiedzi
     */
    public static UserResponse of(UserDto userDto) {
        return UserResponse.builder()
                .id(userDto.id())
                .fullName(userDto.fullName())
                .contact(userDto.contact())
                .build();
    }

    /**
     * Tworzy nową instancję buildera dla {@link UserResponse}.
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link UserResponse}, umożliwiający elastyczne tworzenie instancji.
     */
    public static class Builder {

        private String id;
        private FullNameDto fullName;
        private UserContactDto contact;

        /** Ustawia identyfikator użytkownika.
         * 
         * @param id identyfikator
         * @return builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Ustawia imię i nazwisko użytkownika.
         * 
         * @param fullName imię i nazwisko
         * @return builder
         */
        public Builder fullName(FullNameDto fullName) {
            this.fullName = fullName;
            return this;
        }

        /** Ustawia dane kontaktowe użytkownika.
         * 
         * @param contact dane kontaktowe
         * @return builder
         */
        public Builder contact(UserContactDto contact) {
            this.contact = contact;
            return this;
        }

        /**
         * Tworzy instancję {@link UserResponse} z ustawionymi danymi.
         *
         * @return gotowy obiekt odpowiedzi
         */
        public UserResponse build() {
            return new UserResponse(id, fullName, contact);
        }

    }

}
