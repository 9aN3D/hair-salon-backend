package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.port.primary.MemberService;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.domain.dto.MemberDto;
import pl.edu.wit.domain.exception.member.MemberNotFoundException;
import pl.edu.wit.domain.model.member.Member;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppMemberService implements MemberService {

    private final MemberDao memberDao;

    @Override
    public MemberDto getOne(String authDetailsId) {
        log.trace("Getting member {authDetailsId: {}}", authDetailsId);
        var memberDto = memberDao.findOne(authDetailsId)
                .map(Member::toDto)
                .orElseThrow(() -> new MemberNotFoundException(
                        format("Member not found by authDetailsId: %s", authDetailsId))
                );
        log.info("Got member {dto: {}}", memberDto);
        return memberDto;
    }

}
