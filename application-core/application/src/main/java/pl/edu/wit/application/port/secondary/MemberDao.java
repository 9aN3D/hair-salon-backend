package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.domain.model.member.Member;

import java.util.Optional;

public interface MemberDao {

    String save(Member member);

    Optional<Member> findOne(MemberFindQuery query);

}
