package pl.edu.wit.application.port.primary;

import pl.edu.wit.domain.dto.MemberDto;

public interface MemberService {

    MemberDto getOne(String authDetailsId);

}
