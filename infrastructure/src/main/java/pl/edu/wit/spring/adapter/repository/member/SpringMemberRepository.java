package pl.edu.wit.spring.adapter.repository.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.member.domain.Member;
import pl.edu.wit.member.port.secondary.MemberRepository;
import pl.edu.wit.spring.adapter.repository.member.mapper.MemberMapper;

@Repository
@RequiredArgsConstructor
public class SpringMemberRepository implements MemberRepository {

    private final MongoMemberRepository repository;
    private final MemberMapper mapper;

    @Override
    public String save(Member member) {
        return repository.save(mapper.toDocument(member)).getId();
    }

}
