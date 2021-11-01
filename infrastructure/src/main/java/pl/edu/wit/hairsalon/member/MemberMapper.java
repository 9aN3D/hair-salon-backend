package pl.edu.wit.hairsalon.member;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.member.dto.MemberDto;

@Component
@Mapper(componentModel = "spring")
abstract class MemberMapper {

    abstract MemberDocument toDocument(MemberDto memberDto);

    abstract MemberDto toDto(MemberDocument memberDocument);

}
