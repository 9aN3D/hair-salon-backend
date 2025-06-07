package pl.edu.wit.hairsalon.authDetails.dto;

import lombok.Builder;

@Builder
public record AuthDetailsDto(String id, String email, String password, AuthDetailsStatusDto status, AuthDetailsRoleDto role) {

}
