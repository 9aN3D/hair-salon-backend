package pl.edu.wit.member.port.secondary;

import pl.edu.wit.member.domain.Member;

public interface MemberRepository {

    String save(Member member);

}
