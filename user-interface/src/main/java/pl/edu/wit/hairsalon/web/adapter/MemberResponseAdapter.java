package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.web.response.MemberResponse;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

@Service
@RequiredArgsConstructor
public class MemberResponseAdapter {

    private final MemberFacade memberFacade;

    public MemberResponse findOne(String authDetailsId) {
        return MemberResponse.of(memberFacade.findOne(authDetailsId));
    }

    public void update(String memberId, MemberUpdateCommand command) {
        memberFacade.update(memberId, command);
    }

    public Page<MemberResponse> findAll(MemberFindQuery findQuery, Pageable pageable) {
        return memberFacade.findAll(findQuery, pageable)
                .map(MemberResponse::of);
    }

}
