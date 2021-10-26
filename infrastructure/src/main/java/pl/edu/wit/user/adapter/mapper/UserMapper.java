package pl.edu.wit.user.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;
import pl.edu.wit.user.dto.UserDto;
import pl.edu.wit.auth.details.adapter.mapper.AuthDetailsMapper;
import pl.edu.wit.user.adapter.model.UserDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private AuthDetailsMapper authDetailsMapper;

    @Autowired
    public void setAuthDetailsMapper(AuthDetailsMapper authDetailsMapper) {
        this.authDetailsMapper = authDetailsMapper;
    }

    @Mapping(source = "userDocument", target = "authDetails", qualifiedByName = "toAuthDetailsDto")
    public abstract UserDto toDto(UserDocument userDocument);

    @Named("toAuthDetailsDto")
    AuthDetailsDto toAuthDetailsDto(UserDocument userDocument) {
        return authDetailsMapper.toDto(userDocument.getAuthDetails());
    }

}
