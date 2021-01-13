package pl.edu.wit.spring.adapter.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.domain.model.member.Member;
import pl.edu.wit.spring.adapter.persistence.auth_details.MongoAuthDetailsRepository;
import pl.edu.wit.spring.adapter.persistence.auth_details.mapper.AuthDetailsMapper;
import pl.edu.wit.spring.adapter.persistence.member.mapper.MemberMapper;
import pl.edu.wit.spring.adapter.persistence.member.model.QMemberDocument;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoMemberDao implements MemberDao {

    private final MongoMemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MongoAuthDetailsRepository authDetailsRepository;
    private final AuthDetailsMapper authDetailsMapper;

    @Override
    public String save(Member member) {
        var authDetailsDocument = authDetailsMapper.toDocument(member.getAuthDetails());
        var savedAuthDetailsDocument = authDetailsRepository.save(authDetailsDocument);
        var memberDocument = memberMapper.toDocument(member, savedAuthDetailsDocument);
        return memberRepository.save(memberDocument).getId();
    }

    @Override
    public Optional<Member> findOne(String authDetailsId) {
        var qMember = new QMemberDocument("member");
        return memberRepository.findOne(qMember.authDetails.id.eq(authDetailsId))
                .map(memberMapper::toDomain);
    }

}
