package pl.edu.wit.application.domain.model.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.domain.model.StringNotBlank;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.dto.UserDto;
import pl.edu.wit.application.exception.member.MemberNotValidException;
import pl.edu.wit.application.exception.user.UserNotValidException;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class User {

    private final String id;
    private String name;
    private String surname;
    private final AuthDetails authDetails;
    private final LocalDateTime registrationDateTime;

    public User(String id,
                String name,
                String surname,
                AuthDetails authDetails,
                LocalDateTime registrationDateTime) {
        this.id = setId(id);
        this.name = setName(name);
        this.surname = setSurname(surname);
        this.authDetails = setAuthDetails(authDetails);
        this.registrationDateTime = setRegistrationDateTime(registrationDateTime);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public AuthDetails getAuthDetails() {
        return authDetails;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
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

    private String setId(String id) {
        if (nonNull(id)) {
            return id;
        }
        throw new UserNotValidException("User id cannot be null");
    }

    private String setName(String name) {
        return new StringNotBlank(name).validate();
    }

    private String setSurname(String surname) {
        return new StringNotBlank(surname).validate();
    }

    private AuthDetails setAuthDetails(AuthDetails authDetails) {
        if (nonNull(authDetails)) {
            return authDetails;
        }
        throw new MemberNotValidException("User auth details cannot be null");
    }

    private LocalDateTime setRegistrationDateTime(LocalDateTime registrationDateTime) {
        if (nonNull(registrationDateTime)) {
            return registrationDateTime;
        }
        throw new MemberNotValidException("User registration date time cannot be null");
    }

}
