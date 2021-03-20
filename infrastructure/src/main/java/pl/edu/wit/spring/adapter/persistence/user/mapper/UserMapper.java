package pl.edu.wit.spring.adapter.persistence.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.domain.model.user.User;
import pl.edu.wit.spring.adapter.persistence.auth_details.mapper.AuthDetailsMapper;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;
import pl.edu.wit.spring.adapter.persistence.user.model.UserDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private AuthDetailsMapper authDetailsMapper;

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "authDetailsDocument", target = "authDetails")
    public abstract UserDocument toDocument(User user, AuthDetailsDocument authDetailsDocument);

    public User toDomain(UserDocument userDocument) {
        return User.builder()
                .id(userDocument.getId())
                .name(userDocument.getName())
                .surname(userDocument.getSurname())
                .authDetails(authDetailsMapper.toDomain(userDocument.getAuthDetails()))
                .registrationDateTime(userDocument.getRegistrationDateTime())
                .build();
    }

    @Autowired
    public void setAuthDetailsMapper(AuthDetailsMapper authDetailsMapper) {
        this.authDetailsMapper = authDetailsMapper;
    }

}
