package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.MemberUpdateCommand;
import pl.edu.wit.application.dto.MemberDto;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

public interface MemberService {

    MemberDto getOne(String authDetailsId);

    void update(String memberId, MemberUpdateCommand command);

    PageSlice<MemberDto> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery);

}
