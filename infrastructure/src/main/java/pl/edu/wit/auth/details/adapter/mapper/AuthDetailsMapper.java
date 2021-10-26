package pl.edu.wit.auth.details.adapter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;
import pl.edu.wit.auth.details.adapter.model.AuthDetailsDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthDetailsMapper {

    public abstract AuthDetailsDto toDto(AuthDetailsDocument authDetailsDocument);

    public abstract AuthDetailsDocument toDocument(AuthDetailsDto authDetailsDto);

}
