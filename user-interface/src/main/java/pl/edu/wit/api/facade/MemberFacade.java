package pl.edu.wit.api.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.api.response.MemberResponse;
import pl.edu.wit.application.command.member.MemberUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.MemberFindQuery;

public interface MemberFacade {

    MemberResponse findOne(String authDetailsId);

    void update(String memberId, MemberUpdateCommand command);

    PageSlice<MemberResponse> findAll(MemberFindQuery findQuery, Pageable pageable);

}
