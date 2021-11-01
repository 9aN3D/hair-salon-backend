package pl.edu.wit.hairsalon.authdetails;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;

@Component
@Mapper(componentModel = "spring")
abstract class AuthDetailsMapper {

    abstract AuthDetailsDto toDto(AuthDetailsDocument authDetailsDocument);

    abstract AuthDetailsDocument toDocument(AuthDetailsDto authDetailsDto);

}
