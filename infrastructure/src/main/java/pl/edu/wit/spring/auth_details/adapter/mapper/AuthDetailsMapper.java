package pl.edu.wit.spring.auth_details.adapter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.spring.auth_details.adapter.model.AuthDetails;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthDetailsMapper {

    public abstract AuthDetails toAuthDetails(AuthDetailsDto dto);

}
