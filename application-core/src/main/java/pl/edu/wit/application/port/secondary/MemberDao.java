package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.dto.MemberDto;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

import java.util.Optional;

public interface MemberDao {

    String save(MemberDto member);

    Optional<MemberDto> findOne(MemberFindQuery query);

    PageSlice<MemberDto> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery);

}
