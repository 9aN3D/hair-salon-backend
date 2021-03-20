package pl.edu.wit.spring.adapter.persistence.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.query.MemberFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.spring.adapter.persistence.PageableMapper;
import pl.edu.wit.spring.adapter.persistence.auth_details.MongoAuthDetailsRepository;
import pl.edu.wit.spring.adapter.persistence.auth_details.mapper.AuthDetailsMapper;
import pl.edu.wit.spring.adapter.persistence.member.mapper.MemberMapper;
import pl.edu.wit.spring.adapter.persistence.member.model.QMemberDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class MongoMemberDao implements MemberDao {

    private final MongoMemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MongoAuthDetailsRepository authDetailsRepository;
    private final AuthDetailsMapper authDetailsMapper;
    private final PageableMapper<Member> pageableMapper;

    @Override
    public String save(Member member) {
        var authDetailsDocument = authDetailsMapper.toDocument(member.getAuthDetails());
        var savedAuthDetailsDocument = authDetailsRepository.save(authDetailsDocument);
        var memberDocument = memberMapper.toDocument(member, savedAuthDetailsDocument);
        return memberRepository.save(memberDocument).getId();
    }

    @Override
    public Optional<Member> findOne(MemberFindQuery query) {
        var qMember = QMemberDocument.memberDocument;
        var builder = new BooleanBuilder();
        ofNullable(query.getMemberId()).ifPresent(memberId -> builder.and(qMember.id.eq(memberId)));
        ofNullable(query.getAuthDetailsId()).ifPresent(authDetailsId -> builder.and(qMember.authDetails.id.eq(authDetailsId)));

        return ofNullable(builder.getValue())
                .flatMap(memberRepository::findOne)
                .map(memberMapper::toDomain);
    }

    @Override
    public PageSlice<Member> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery) {
        var pageable = pageableMapper.toPageable(pageableQuery);
        var page = ofNullable(buildPredicate(findQuery))
                .map(predicate -> memberRepository.findAll(predicate, pageable))
                .orElseGet(() -> memberRepository.findAll(pageable))
                .map(memberMapper::toDomain);
        return pageableMapper.toPageSlice(page);
    }

    private Predicate buildPredicate(MemberFindQuery findQuery) {
        var qMember = QMemberDocument.memberDocument;
        var builder = new BooleanBuilder();
        buildLikeFullName(findQuery, qMember, builder);
        return builder.getValue();
    }

    private void buildLikeFullName(MemberFindQuery findQuery, QMemberDocument qMember, BooleanBuilder builder) {
        ofNullable(findQuery.getFullName())
                .map(String::trim)
                .filter(String::isBlank)
                .map(String::toLowerCase)
                .ifPresent(fullName -> builder.and(qMember.name.lower().like(fullName)).or(qMember.surname.lower().like(fullName)));
    }

}
