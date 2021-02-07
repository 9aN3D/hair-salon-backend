package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.MemberRegisterCommand;
import pl.edu.wit.application.factory.MemberFactory;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.primary.MemberRegistrationService;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.exception.auth_details.AuthDetailsAlreadyExists;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppMemberRegistrationService implements MemberRegistrationService {

    private final MemberDao memberDao;
    private final MemberFactory factory;
    private final AuthDetailsService authDetailsService;

    @Override
    public void register(MemberRegisterCommand command) {
        log.trace("Registering member {command: {}}", command);
        var member = factory.createNewMember(command);
        throwIfExistByCommandEmail(command.getEmail());
        var savedMemberId = memberDao.save(member);
        log.info("Registered member {savedMemberId: {}}", savedMemberId);
    }

    private void throwIfExistByCommandEmail(String email) {
        if (authDetailsService.existByEmail(email)) {
            throw new AuthDetailsAlreadyExists(
                    format("Auth details already exists by email: %s", email));
        }
    }

}
