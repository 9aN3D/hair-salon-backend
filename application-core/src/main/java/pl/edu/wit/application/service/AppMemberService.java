package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.AuthDetailsUpdateCommand;
import pl.edu.wit.application.command.MemberUpdateCommand;
import pl.edu.wit.application.domain.model.EncodedPassword;
import pl.edu.wit.application.domain.model.NotBlankPhoneNumber;
import pl.edu.wit.application.domain.model.PossiblePhoneNumber;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsPassword;
import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.dto.MemberDto;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.exception.member.MemberNotFoundException;
import pl.edu.wit.application.port.primary.MemberService;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.port.secondary.PasswordEncoder;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;
import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
public class AppMemberService implements MemberService {

    private final MemberDao memberDao;
    private final PhoneNumberProvider phoneNumberProvider;
    private final PasswordEncoder passwordEncoder;

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
    public void update(String memberId, MemberUpdateCommand command) {
        log.trace("Updating member {memberId: {}, command: {}}", memberId, command);
        var member = getOneFromDaoOrThrow(memberId);
        member.update(command);
        updatePhoneNumber(member, command.getPhone());
        updateAuthDetails(member, command.toAuthDetailsUpdateCommand());
        memberDao.save(member);
        log.info("Updated member {member: {}}", member);
    }

    @Override
    public PageSlice<MemberDto> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery) {
        log.trace("Searching members {findQuery: {}, pageableQuery: {}}", findQuery, pageableQuery);
        var page = memberDao.findAll(findQuery, pageableQuery)
                .map(Member::toDto);
        log.info("Searched members {contentTotalElements: {}, contentSize: {}}", page.getTotalElements(), page.getSize());
        return page;
    }

    private void updatePhoneNumber(Member member, String phoneNumber) {
        ofNullable(phoneNumber)
                .map(NotBlankPhoneNumber::new)
                .map(notBlankPhoneNumber -> new PossiblePhoneNumber(notBlankPhoneNumber, phoneNumberProvider))
                .ifPresent(member::updatePhoneNumber);
    }

    private void updateAuthDetails(Member member, AuthDetailsUpdateCommand command) {
        var encodedPassword = ofNullable(command.getPassword())
                .map(AuthDetailsPassword::new)
                .map(authDetailsPassword -> new EncodedPassword(authDetailsPassword, passwordEncoder))
                .orElse(null);
        member.updateAuthDetails(command, encodedPassword);
    }

    private Member getOneFromDaoOrThrow(String memberId) {
        return memberDao.findOne(MemberFindQuery.ofMemberId(memberId))
                .orElseThrow(() -> new MemberNotFoundException(
                        format("Member not found by memberId: %s", memberId))
                );
    }

}
