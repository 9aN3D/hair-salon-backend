package pl.edu.wit.spring.adapter.repository.auth_details.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.dto.AuthDetailsDto.AuthDetailsDtoBuilder;
import pl.edu.wit.domain.model.auth_details.AuthDetails;
import pl.edu.wit.spring.adapter.repository.auth_details.document.AuthDetailsDocument;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-06T18:26:01+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14 (Oracle Corporation)"
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
        authDetailsDocument.setStatus( authDetails.getStatus() );
        authDetailsDocument.setRole( authDetails.getRole() );

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
        authDetailsDto.status( document.getStatus() );
        authDetailsDto.role( document.getRole() );

        return authDetailsDto.build();
    }
}
