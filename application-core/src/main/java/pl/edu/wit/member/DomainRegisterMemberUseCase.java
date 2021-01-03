package pl.edu.wit.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.auth_details.port.primary.CreateAuthDetailsUseCase;
import pl.edu.wit.auth_details.shared.command.CreateAuthDetailsCommand;
import pl.edu.wit.member.domain.Member;
import pl.edu.wit.member.port.primary.RegisterMemberUseCase;
import pl.edu.wit.member.port.secondary.MemberRepository;
import pl.edu.wit.member.shared.command.RegisterMemberCommand;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.auth_details.shared.AuthDetailsRole.MEMBER;
import static pl.edu.wit.auth_details.shared.AuthDetailsStatus.ACTIVE;

@Slf4j
@RequiredArgsConstructor
final class DomainRegisterMemberUseCase implements RegisterMemberUseCase {

    private final IdGenerator idGenerator;
    private final MemberRepository memberRepository;
    private final CreateAuthDetailsUseCase createAuthDetailsUseCase;

    @Override
    public void register(RegisterMemberCommand command) {
        log.trace("Registering member {command: {}}", command);
        var authDetailsId = createAuthDetailsUseCase.create(buildCreateAuthDetailsCommand(command));
        var member = new Member(idGenerator, authDetailsId, now(), command);
        var savedMemberId = memberRepository.save(member);
        log.info("Registered member {savedMemberId: {}}", savedMemberId);
    }

    private CreateAuthDetailsCommand buildCreateAuthDetailsCommand(RegisterMemberCommand command) {
        return CreateAuthDetailsCommand.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .authDetailsStatus(ACTIVE)
                .authDetailsRole(MEMBER)
                .build();
    }

}
