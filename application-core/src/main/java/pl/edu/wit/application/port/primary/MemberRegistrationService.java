package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.member.MemberRegisterCommand;

public interface MemberRegistrationService {

    void register(MemberRegisterCommand command);

}
