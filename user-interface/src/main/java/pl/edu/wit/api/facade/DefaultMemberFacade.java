package pl.edu.wit.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.api.mapper.PageableParamsMapper;
import pl.edu.wit.api.response.MemberResponse;
import pl.edu.wit.application.command.member.MemberUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.port.primary.MemberService;
import pl.edu.wit.application.query.MemberFindQuery;

@Service
@RequiredArgsConstructor
public class DefaultMemberFacade implements MemberFacade {

    private final MemberService memberService;
    private final PageableParamsMapper pageableParamsMapper;

    @Override
    public MemberResponse findOne(String authDetailsId) {
        return MemberResponse.of(memberService.findOne(authDetailsId));
    }

    @Override
    public void update(String memberId, MemberUpdateCommand command) {
        memberService.update(memberId, command);
    }

    @Override
    public PageSlice<MemberResponse> findAll(MemberFindQuery findQuery, Pageable pageable) {
        return memberService.findAll(findQuery, pageableParamsMapper.toPageableParamsQuery(pageable))
                .map(MemberResponse::of);
    }

}
