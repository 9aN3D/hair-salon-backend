package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.RegisterMemberCommand;

public interface MemberRegistrationService {

    void register(RegisterMemberCommand command);

}
