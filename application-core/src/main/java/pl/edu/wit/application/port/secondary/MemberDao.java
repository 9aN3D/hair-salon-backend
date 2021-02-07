package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.query.MemberFindQuery;

import java.util.Optional;

public interface MemberDao {

    String save(Member member);

    Optional<Member> findOne(MemberFindQuery query);

}
