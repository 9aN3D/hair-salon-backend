package pl.edu.wit.spring.adapter.persistence.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.domain.model.NotBlankPhoneNumber;
import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.domain.model.member.MemberAgreements;
import pl.edu.wit.spring.adapter.persistence.auth_details.mapper.AuthDetailsMapper;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;
import pl.edu.wit.spring.adapter.persistence.member.model.MemberDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class MemberMapper {

    private AuthDetailsMapper authDetailsMapper;

    @Mapping(source = "member.id", target = "id")
    @Mapping(source = "authDetailsDocument", target = "authDetails")
    @Mapping(source = "member", target = "phoneNumber", qualifiedByName = "phoneNumber")
    public abstract MemberDocument toDocument(Member member, AuthDetailsDocument authDetailsDocument);

    @Named("phoneNumber")
    String phoneNumberToString(Member member) {
        return member.getPhoneNumber().value();
    }

    public Member toDomain(MemberDocument memberDocument) {
        return Member.builder()
                .id(memberDocument.getId())
                .name(memberDocument.getName())
                .surname(memberDocument.getSurname())
                .phoneNumber(new NotBlankPhoneNumber(memberDocument.getPhoneNumber()))
                .agreements(new MemberAgreements(memberDocument.getAgreements()))
                .authDetails(authDetailsMapper.toDomain(memberDocument.getAuthDetails()))
                .registrationDateTime(memberDocument.getRegistrationDateTime())
                .build();
    }

    @Autowired
    public void setAuthDetailsMapper(AuthDetailsMapper authDetailsMapper) {
        this.authDetailsMapper = authDetailsMapper;
    }

}
