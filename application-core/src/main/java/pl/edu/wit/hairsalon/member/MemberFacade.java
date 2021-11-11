package pl.edu.wit.hairsalon.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.member.command.MemberRegisterCommand;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

public interface MemberFacade {

    String register(MemberRegisterCommand command);

    void update(String memberId, MemberUpdateCommand command);

    MemberDto findOne(String memberId);

    Page<MemberDto> findAll(MemberFindQuery findQuery, Pageable pageable);

    boolean exist(MemberFindQuery findQuery);

}
