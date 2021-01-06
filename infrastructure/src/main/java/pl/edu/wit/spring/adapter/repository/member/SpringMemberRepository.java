package pl.edu.wit.spring.adapter.repository.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wit.application.port.secondary.MemberRepository;
import pl.edu.wit.domain.model.member.Member;
import pl.edu.wit.spring.adapter.repository.member.mapper.MemberMapper;

@Repository
@RequiredArgsConstructor
public class SpringMemberRepository implements MemberRepository {

    private final MongoMemberRepository repository;
    private final MemberMapper mapper;

    @Override
    @Transactional
    public String save(Member member) {
        return repository.save(mapper.toDocument(member)).getId();
    }

}
