package pl.edu.wit.spring.auth_details.adapter.mapper;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.edu.wit.auth_details.shared.Role;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.spring.auth_details.adapter.model.AuthDetails;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-27T22:07:20+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9.1 (Ubuntu)"
)
@Component
public class AuthDetailsMapperImpl extends AuthDetailsMapper {

    @Override
    public AuthDetails toAuthDetails(AuthDetailsDto dto) {
        if ( dto == null ) {
            return null;
        }

        AuthDetails authDetails = new AuthDetails();

        authDetails.setId( dto.getId() );
        authDetails.setEmail( dto.getEmail() );
        authDetails.setPassword( dto.getPassword() );
        Set<Role> set = dto.getRoles();
        if ( set != null ) {
            authDetails.setRoles( new HashSet<Role>( set ) );
        }

        return authDetails;
    }
}
