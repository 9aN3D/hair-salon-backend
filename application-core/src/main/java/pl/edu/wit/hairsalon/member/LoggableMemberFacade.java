package pl.edu.wit.hairsalon.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.member.command.MemberRegisterCommand;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

import static java.util.Objects.requireNonNull;

class LoggableMemberFacade implements MemberFacade {

    private final Logger log;
    private final MemberFacade delegate;

    LoggableMemberFacade(MemberFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableMemberFacade.class);
        this.delegate = delegate;
    }

    @Override
    public String register(MemberRegisterCommand command) {
        log.trace("Registering member {command: {}}", command);
        requireNonNull(command, "Member register command must not be null");
        var result = delegate.register(command);
        log.info("Registered member {id: {}}", result);
        return result;
    }

    @Override
    public void update(String memberId, MemberUpdateCommand command) {
        log.trace("Updating member {id: {}, command: {}}", memberId, command);
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(command, "Member update command must not be null");
        delegate.update(memberId, command);
        log.info("Updated member {id: {}}", memberId);
    }

    @Override
    public MemberDto findOne(String memberId) {
        log.trace("Getting member {id: {}}", memberId);
        requireNonNull(memberId, "Member id must not be null");
        var dto = delegate.findOne(memberId);
        log.info("Got member {dto: {}}", dto);
        return dto;
    }

    @Override
    public Page<MemberDto> findAll(MemberFindQuery findQuery, Pageable pageable) {
        log.trace("Searching members {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Member find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = delegate.findAll(findQuery, pageable);
        log.info("Searched members {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

    @Override
    public boolean exist(MemberFindQuery findQuery) {
        log.trace("Searching exist members {findQuery: {}}", findQuery);
        requireNonNull(findQuery, "Member find query must not be null");
        var isExist = delegate.exist(findQuery);
        log.info("Searched exist members {result: {}}", isExist);
        return isExist;
    }

}
