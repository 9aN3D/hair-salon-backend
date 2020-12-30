package pl.edu.wit.spring.adapter.repository.auth_details.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.auth_details.domain.AuthDetails;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.spring.adapter.repository.auth_details.document.AuthDetailsDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthDetailsMapper {

    public abstract AuthDetailsDocument toDocument(AuthDetails authDetails);

    public abstract AuthDetailsDto toDto(AuthDetailsDocument document);

}
