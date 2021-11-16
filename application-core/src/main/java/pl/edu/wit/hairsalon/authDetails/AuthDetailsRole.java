package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto;

enum AuthDetailsRole {

    MEMBER,
    HAIRDRESSER,
    ADMIN;

    AuthDetailsRoleDto toDto() {
        return AuthDetailsRoleDto.valueOf(this.name());
    }

    static AuthDetailsRole valueOf(AuthDetailsRoleDto role) {
        return valueOf(role.name());
    }
}
