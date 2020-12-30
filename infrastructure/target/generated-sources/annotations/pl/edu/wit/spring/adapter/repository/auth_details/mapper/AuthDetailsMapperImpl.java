package pl.edu.wit.spring.adapter.repository.auth_details.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.edu.wit.auth_details.domain.AuthDetails;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto.AuthDetailsDtoBuilder;
import pl.edu.wit.spring.adapter.repository.auth_details.document.AuthDetailsDocument;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-30T22:22:14+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9.1 (Ubuntu)"
)
@Component
public class AuthDetailsMapperImpl extends AuthDetailsMapper {

    @Override
    public AuthDetailsDocument toDocument(AuthDetails authDetails) {
        if ( authDetails == null ) {
            return null;
        }

        AuthDetailsDocument authDetailsDocument = new AuthDetailsDocument();

        authDetailsDocument.setId( authDetails.getId() );
        authDetailsDocument.setEmail( authDetails.getEmail() );
        authDetailsDocument.setPassword( authDetails.getPassword() );

        return authDetailsDocument;
    }

    @Override
    public AuthDetailsDto toDto(AuthDetailsDocument document) {
        if ( document == null ) {
            return null;
        }

        AuthDetailsDtoBuilder authDetailsDto = AuthDetailsDto.builder();

        authDetailsDto.id( document.getId() );
        authDetailsDto.email( document.getEmail() );
        authDetailsDto.password( document.getPassword() );

        return authDetailsDto.build();
    }
}
