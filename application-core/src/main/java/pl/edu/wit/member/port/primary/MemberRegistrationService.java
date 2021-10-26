package pl.edu.wit.member.port.primary;

import pl.edu.wit.member.command.MemberRegisterCommand;

public interface MemberRegistrationService {

    void register(MemberRegisterCommand command);

}
