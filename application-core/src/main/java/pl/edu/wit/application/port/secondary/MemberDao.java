package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

import java.util.Optional;

public interface MemberDao {

    String save(Member member);

    Optional<Member> findOne(MemberFindQuery query);

    PageSlice<Member> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery);

}
