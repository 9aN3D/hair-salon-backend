package pl.edu.wit.application.port.secondary;

import pl.edu.wit.domain.model.member.Member;

public interface MemberRepository {

    String save(Member member);

}
