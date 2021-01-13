package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.UpdateMemberCommand;
import pl.edu.wit.application.factory.MemberFactory;
import pl.edu.wit.application.port.primary.MemberService;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.domain.dto.MemberDto;
import pl.edu.wit.domain.exception.member.MemberNotFoundException;
import pl.edu.wit.domain.model.member.Member;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppMemberService implements MemberService {

    private final MemberDao memberDao;
    private final MemberFactory memberFactory;

    @Override
    public MemberDto getOne(String authDetailsId) {
        log.trace("Getting member {authDetailsId: {}}", authDetailsId);
        var memberDto = memberDao.findOne(MemberFindQuery.ofAuthDetailsId(authDetailsId))
                .map(Member::toDto)
                .orElseThrow(() -> new MemberNotFoundException(
                        format("Member not found by authDetailsId: %s", authDetailsId))
                );
        log.info("Got member {dto: {}}", memberDto);
        return memberDto;
    }

    @Override
    public void update(String memberId, UpdateMemberCommand command) {
        log.trace("Updating member {memberId: {}, command: {}}", memberId, command);
        var member = getOneFromDaoOrThrow(memberId);
        var updatedMember = memberFactory.updateMember(command, member);
        memberDao.save(updatedMember);
        log.info("Updated member {member: {}}", member);
    }

    private Member getOneFromDaoOrThrow(String memberId) {
        return memberDao.findOne(MemberFindQuery.ofMemberId(memberId))
                .orElseThrow(() -> new MemberNotFoundException(
                        format("Member not found by memberId: %s", memberId))
                );
    }

}
