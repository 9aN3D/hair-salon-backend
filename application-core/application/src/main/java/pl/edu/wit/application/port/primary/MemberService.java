package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.UpdateMemberCommand;
import pl.edu.wit.domain.dto.MemberDto;

public interface MemberService {

    MemberDto getOne(String authDetailsId);

    void update(String memberId, UpdateMemberCommand command);

}
