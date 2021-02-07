package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.MemberUpdateCommand;
import pl.edu.wit.application.dto.MemberDto;

public interface MemberService {

    MemberDto getOne(String authDetailsId);

    void update(String memberId, MemberUpdateCommand command);

}
