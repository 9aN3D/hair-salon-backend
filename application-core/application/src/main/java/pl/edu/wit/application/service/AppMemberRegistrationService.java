package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.RegisterMemberCommand;
import pl.edu.wit.application.factory.MemberFactory;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.primary.MemberRegistrationService;
import pl.edu.wit.application.port.secondary.MemberRepository;

@Slf4j
@RequiredArgsConstructor
public class AppMemberRegistrationService implements MemberRegistrationService {

    private final MemberRepository memberRepository;
    private final AuthDetailsService authDetailsService;
    private final MemberFactory factory;

    @Override
    public void register(RegisterMemberCommand command) {
        log.trace("Registering member {command: {}}", command);
        var createAuthDetailsCommand = factory.buildCreateAuthDetailsCommand(command);
        var authDetailsId = authDetailsService.create(createAuthDetailsCommand);
        var member = factory.createNewMember(authDetailsId, command);
        var savedMemberId = memberRepository.save(member);
        log.info("Registered member {savedMemberId: {}}", savedMemberId);
    }

}
