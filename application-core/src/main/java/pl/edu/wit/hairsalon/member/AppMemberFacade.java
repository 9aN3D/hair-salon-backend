package pl.edu.wit.hairsalon.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.member.command.MemberRegisterCommand;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

import static java.util.Objects.requireNonNull;

class AppMemberFacade implements MemberFacade {

    private final MemberPort memberPort;
    private final MemberRegistration memberRegistration;
    private final MemberUpdater memberUpdater;

    AppMemberFacade(MemberPort memberPort, MemberRegistration memberRegistration, MemberUpdater memberUpdater) {
        this.memberPort = memberPort;
        this.memberRegistration = memberRegistration;
        this.memberUpdater = memberUpdater;
    }

    @Override
    public String register(MemberRegisterCommand command) {
        requireNonNull(command, "Member register command must not be null");
        var registeredMemberDto = memberRegistration.register(command);
        return registeredMemberDto.id();
    }

    @Override
    public void update(String memberId, MemberUpdateCommand command) {
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(command, "Member update command must not be null");
        memberUpdater.update(memberId, command);
    }

    @Override
    public MemberDto findOne(String memberId) {
        requireNonNull(memberId, "Member id must not be null");
        return memberPort.findOneOrThrow(MemberFindQuery.withId(memberId));
    }

    @Override
    public Page<MemberDto> findAll(MemberFindQuery findQuery, Pageable pageable) {
        requireNonNull(findQuery, "Member find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        return memberPort.findAll(findQuery, pageable);
    }

    @Override
    public boolean exist(MemberFindQuery findQuery) {
        requireNonNull(findQuery, "Member find query must not be null");
        return memberPort.exist(findQuery);
    }
}
