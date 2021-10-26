package pl.edu.wit.web.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.web.response.MemberResponse;
import pl.edu.wit.member.command.MemberUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.member.query.MemberFindQuery;

public interface MemberFacade {

    MemberResponse findOne(String authDetailsId);

    void update(String memberId, MemberUpdateCommand command);

    PageSlice<MemberResponse> findAll(MemberFindQuery findQuery, Pageable pageable);

}
