package pl.edu.wit.spring.adapter.repository.member.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.domain.model.member.Member;
import pl.edu.wit.spring.adapter.repository.member.document.MemberDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class MemberMapper {

    public abstract MemberDocument toDocument(Member member);

}
