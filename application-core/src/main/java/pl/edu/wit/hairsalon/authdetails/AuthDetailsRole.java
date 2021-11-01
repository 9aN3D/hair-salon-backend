package pl.edu.wit.hairsalon.authdetails;

import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsRoleDto;

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
