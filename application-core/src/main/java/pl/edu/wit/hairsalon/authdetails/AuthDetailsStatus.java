package pl.edu.wit.hairsalon.authdetails;

import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsStatusDto;

enum AuthDetailsStatus {

    ACTIVE,
    NOT_ACTIVE,
    BANNED;

    AuthDetailsStatusDto toDto() {
        return AuthDetailsStatusDto.valueOf(this.name());
    }
}
