package pl.edu.wit.spring.adapter.repository.member.mapper;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.edu.wit.domain.model.member.Member;
import pl.edu.wit.domain.model.member.MemberAgreement;
import pl.edu.wit.spring.adapter.repository.member.document.MemberDocument;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-06T19:50:57+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl extends MemberMapper {

    @Override
    public MemberDocument toDocument(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDocument memberDocument = new MemberDocument();

        memberDocument.setId( member.getId() );
        memberDocument.setName( member.getName() );
        memberDocument.setSurname( member.getSurname() );
        memberDocument.setPhoneNumber( member.getPhoneNumber() );
        Set<MemberAgreement> set = member.getAgreements();
        if ( set != null ) {
            memberDocument.setAgreements( new HashSet<MemberAgreement>( set ) );
        }
        memberDocument.setAuthDetailsId( member.getAuthDetailsId() );
        memberDocument.setRegistrationDateTime( member.getRegistrationDateTime() );

        return memberDocument;
    }
}
