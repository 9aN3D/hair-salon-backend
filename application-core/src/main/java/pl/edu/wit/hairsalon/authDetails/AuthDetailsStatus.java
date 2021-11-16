package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

enum AuthDetailsStatus {

    ACTIVE,
    NOT_ACTIVE,
    BANNED;

    AuthDetailsStatusDto toDto() {
        return AuthDetailsStatusDto.valueOf(this.name());
    }
}
