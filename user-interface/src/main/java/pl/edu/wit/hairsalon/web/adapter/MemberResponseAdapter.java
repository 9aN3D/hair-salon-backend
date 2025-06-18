package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;
import pl.edu.wit.hairsalon.web.response.MemberResponse;

@Service
public class MemberResponseAdapter {

    private final MemberFacade memberFacade;

    public MemberResponseAdapter(MemberFacade memberFacade) {
        this.memberFacade = memberFacade;
    }

    public MemberResponse findOne(String memberId) {
        return MemberResponse.of(memberFacade.findOne(memberId));
    }

    public void update(String memberId, MemberUpdateCommand command) {
        memberFacade.update(memberId, command);
    }

    public Page<MemberResponse> findAll(MemberFindQuery findQuery, Pageable pageable) {
        return memberFacade.findAll(findQuery, pageable)
                .map(MemberResponse::of);
    }

}
