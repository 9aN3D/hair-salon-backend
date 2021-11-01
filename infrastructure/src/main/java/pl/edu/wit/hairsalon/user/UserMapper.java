package pl.edu.wit.hairsalon.user;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.user.dto.UserDto;

@Component
@Mapper(componentModel = "spring")
abstract class UserMapper {

    abstract UserDto toDto(UserDocument userDocument);

    abstract UserDocument toDocument(UserDto user);

}
