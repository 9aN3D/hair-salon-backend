package pl.edu.wit.spring.adapter.persistence.auth_details.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.domain.model.Email;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsPassword;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthDetailsMapper {

    @Mapping(source = "authDetails", target = "email", qualifiedByName = "email")
    @Mapping(source = "authDetails", target = "password", qualifiedByName = "password")
    public abstract AuthDetailsDocument toDocument(AuthDetails authDetails);

    @Named("email")
    String emailToString(AuthDetails authDetails) {
        return authDetails.getEmail().value();
    }

    @Named("password")
    String passwordToString(AuthDetails authDetails) {
        return authDetails.getPassword().value();
    }

    public abstract AuthDetailsDto toDto(AuthDetailsDocument document);

    public AuthDetails toDomain(AuthDetailsDocument document) {
        return AuthDetails.builder()
                .id(document.getId())
                .email(new Email(document.getEmail()))
                .password(new AuthDetailsPassword(document.getPassword()))
                .status(document.getStatus())
                .role(document.getRole())
                .build();
    }

}
