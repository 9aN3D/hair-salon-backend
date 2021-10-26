package pl.edu.wit.member.port.primary;

import pl.edu.wit.member.command.MemberUpdateCommand;
import pl.edu.wit.member.dto.MemberDto;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.member.query.MemberFindQuery;
import pl.edu.wit.common.query.PageableParamsQuery;

public interface MemberService {

    MemberDto findOne(String authDetailsId);

    void update(String memberId, MemberUpdateCommand command);

    PageSlice<MemberDto> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery);

}
