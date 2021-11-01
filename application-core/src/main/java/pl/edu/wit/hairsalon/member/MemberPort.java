package pl.edu.wit.hairsalon.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

import java.util.Optional;

public interface MemberPort {

    String save(MemberDto member);

    Optional<MemberDto> findOne(MemberFindQuery query);

    MemberDto findOneOrThrow(MemberFindQuery findQuery);

    Page<MemberDto> findAll(MemberFindQuery findQuery, Pageable pageable);

}
