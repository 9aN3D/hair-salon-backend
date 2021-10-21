package pl.edu.wit.spring.adapter.persistence.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.dto.MemberDto;
import pl.edu.wit.spring.adapter.persistence.auth_details.mapper.AuthDetailsMapper;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;
import pl.edu.wit.spring.adapter.persistence.member.model.MemberDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class MemberMapper {

    private AuthDetailsMapper authDetailsMapper;

    @Autowired
    public void setAuthDetailsMapper(AuthDetailsMapper authDetailsMapper) {
        this.authDetailsMapper = authDetailsMapper;
    }

    @Mapping(source = "memberDto", target = "authDetails", qualifiedByName = "toAuthDetailsDocument")
    public abstract MemberDocument toDocument(MemberDto memberDto);

    @Mapping(source = "memberDocument", target = "authDetails", qualifiedByName = "toAuthDetailsDto")
    public abstract MemberDto toDto(MemberDocument memberDocument);

    @Named("toAuthDetailsDocument")
    AuthDetailsDocument toAuthDetailsDocument(MemberDto memberDto) {
        return authDetailsMapper.toDocument(memberDto.getAuthDetails());
    }

    @Named("toAuthDetailsDto")
    AuthDetailsDto toAuthDetailsDto(MemberDocument memberDocument) {
        return authDetailsMapper.toDto(memberDocument.getAuthDetails());
    }

}
