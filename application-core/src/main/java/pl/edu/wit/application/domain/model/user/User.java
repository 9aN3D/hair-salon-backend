package pl.edu.wit.application.domain.model.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.domain.model.NotBlankString;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.dto.UserDto;
import pl.edu.wit.application.exception.ValidationException;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class User {

    private final String id;
    private final String name;
    private final String surname;
    private final AuthDetails authDetails;
    private final LocalDateTime registrationDateTime;

    public User(String id, String name, String surname, AuthDetails authDetails, LocalDateTime registrationDateTime) {
        this.id = new NotBlankString(id).value();
        this.name = new NotBlankString(name).value();
        this.surname = new NotBlankString(surname).value();
        this.authDetails = ofNullable(authDetails)
                .orElseThrow(() -> new ValidationException("User auth details cannot be null"));
        this.registrationDateTime = ofNullable(registrationDateTime)
                .orElseThrow(() -> new ValidationException("User registration date time cannot be null"));

    }

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .authDetails(authDetails.toDto())
                .registrationDateTime(registrationDateTime)
                .build();
    }

}
