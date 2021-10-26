package pl.edu.wit.web.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.web.mapper.PageableParamsMapper;
import pl.edu.wit.web.response.MemberResponse;
import pl.edu.wit.member.command.MemberUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.member.port.primary.MemberService;
import pl.edu.wit.member.query.MemberFindQuery;

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
