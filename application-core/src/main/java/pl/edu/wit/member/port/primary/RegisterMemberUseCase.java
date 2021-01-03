package pl.edu.wit.member.port.primary;

import pl.edu.wit.member.shared.command.RegisterMemberCommand;

public interface RegisterMemberUseCase {

    void register(RegisterMemberCommand command);

}
