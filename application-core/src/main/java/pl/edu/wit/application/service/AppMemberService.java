package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.member.MemberUpdateCommand;
import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.dto.MemberDto;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.exception.member.MemberNotFoundException;
import pl.edu.wit.application.port.primary.MemberService;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.port.secondary.PasswordEncoder;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;
import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppMemberService implements MemberService {

    private final MemberDao memberDao;
    private final PhoneNumberProvider phoneNumberProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthDetailsDao authDetailsDao;

    @Override
    public MemberDto findOne(String authDetailsId) {
        log.trace("Getting member {authDetailsId: {}}", authDetailsId);
        var memberDto = memberDao.findOne(MemberFindQuery.ofAuthDetailsId(authDetailsId))
                .orElseThrow(() -> new MemberNotFoundException(
                        format("Member not found by authDetailsId: %s", authDetailsId))
                );
        log.info("Got member {dto: {}}", memberDto);
        return memberDto;
    }

    @Override
    public void update(String memberId, MemberUpdateCommand command) {
        log.trace("Updating member {memberId: {}, command: {}}", memberId, command);
        var member = new Member(getOneFromDaoOrThrow(memberId));
        var updatedMember = member.update(command, passwordEncoder, phoneNumberProvider);
        var updatedMemberDto = updatedMember.toDto();
        if (command.isNotBlankEmail() || command.isNotBlankPhone()) {
            authDetailsDao.save(updatedMemberDto.getAuthDetails());
        }
        var updatedMemberId = memberDao.save(updatedMemberDto);
        log.info("Updated member {updatedMemberId: {}, member: {}}", updatedMemberId, updatedMember);
    }

    @Override
    public PageSlice<MemberDto> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery) {
        log.trace("Searching members {findQuery: {}, pageableQuery: {}}", findQuery, pageableQuery);
        var page = memberDao.findAll(findQuery, pageableQuery);
        log.info("Searched members {contentTotalElements: {}, contentSize: {}}", page.getTotalElements(), page.getSize());
        return page;
    }

    private MemberDto getOneFromDaoOrThrow(String memberId) {
        return memberDao.findOne(MemberFindQuery.ofMemberId(memberId))
                .orElseThrow(() -> new MemberNotFoundException(
                        format("Member not found by memberId: %s", memberId))
                );
    }

}
