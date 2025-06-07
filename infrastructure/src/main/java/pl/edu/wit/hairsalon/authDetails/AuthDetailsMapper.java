package pl.edu.wit.hairsalon.authDetails;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true),componentModel = "spring")
abstract class AuthDetailsMapper {

    abstract AuthDetailsDto toDto(AuthDetailsDocument authDetailsDocument);

    abstract AuthDetailsDocument toDocument(AuthDetailsDto authDetailsDto);

}
