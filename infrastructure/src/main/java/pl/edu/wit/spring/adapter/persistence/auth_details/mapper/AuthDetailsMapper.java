package pl.edu.wit.spring.adapter.persistence.auth_details.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthDetailsMapper {

    public abstract AuthDetailsDto toDto(AuthDetailsDocument authDetailsDocument);

    public abstract AuthDetailsDocument toDocument(AuthDetailsDto authDetailsDto);

}
