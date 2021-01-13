package pl.edu.wit.spring.adapter.persistence.auth_details.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.model.Email;
import pl.edu.wit.domain.model.auth_details.AuthDetails;
import pl.edu.wit.domain.model.auth_details.AuthDetailsPassword;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthDetailsMapper {

    public abstract AuthDetailsDocument toDocument(AuthDetails authDetails);

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
