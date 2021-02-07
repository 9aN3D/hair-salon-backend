package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.MemberRegisterCommand;

public interface MemberRegistrationService {

    void register(MemberRegisterCommand command);

}
