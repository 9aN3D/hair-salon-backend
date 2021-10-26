package pl.edu.wit.member.port.secondary;

import pl.edu.wit.member.dto.MemberDto;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.member.query.MemberFindQuery;
import pl.edu.wit.common.query.PageableParamsQuery;

import java.util.Optional;

public interface MemberDao {

    String save(MemberDto member);

    Optional<MemberDto> findOne(MemberFindQuery query);

    PageSlice<MemberDto> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery);

}
