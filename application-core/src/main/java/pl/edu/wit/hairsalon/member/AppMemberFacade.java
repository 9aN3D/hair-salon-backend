package pl.edu.wit.hairsalon.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.member.command.MemberRegisterCommand;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppMemberFacade implements MemberFacade {

    private final MemberPort memberPort;
    private final MemberRegistration memberRegistration;
    private final MemberUpdater memberUpdater;

    @Override
    public String register(MemberRegisterCommand command) {
        log.trace("Registering member {command: {}}", command);
        requireNonNull(command, "Member register command must not be null");
        var registeredMemberDto = memberRegistration.register(command);
        log.info("Registered member {registeredMemberDto: {}}", registeredMemberDto);
        return registeredMemberDto.id();
    }

    @Override
    public void update(String memberId, MemberUpdateCommand command) {
        log.trace("Updating member {id: {}, command: {}}", memberId, command);
        requireNonNull(memberId, "Member id command must not be null");
        requireNonNull(command, "Member update command must not be null");
        var updatedMember = memberUpdater.update(memberId, command);
        log.info("Updated member {id: {}, member: {}}", memberId, updatedMember);
    }

    @Override
    public MemberDto findOne(String memberId) {
        log.trace("Getting member {id: {}}", memberId);
        requireNonNull(memberId, "Member id command must not be null");
        var memberDto = memberPort.findOneOrThrow(MemberFindQuery.withId(memberId));
        log.info("Got member {dto: {}}", memberDto);
        return memberDto;
    }

    @Override
    public Page<MemberDto> findAll(MemberFindQuery findQuery, Pageable pageable) {
        log.trace("Searching members {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Member find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = memberPort.findAll(findQuery, pageable);
        log.info("Searched members {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

    @Override
    public boolean exist(MemberFindQuery findQuery) {
        log.trace("Searching exist members {findQuery: {}}", findQuery);
        requireNonNull(findQuery, "Member find query must not be null");
        var isExist = memberPort.exist(findQuery);
        log.info("Searched exist members {result: {}}", isExist);
        return isExist;
    }

}
