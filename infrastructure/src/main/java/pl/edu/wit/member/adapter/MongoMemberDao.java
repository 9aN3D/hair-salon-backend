package pl.edu.wit.member.adapter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.member.dto.MemberDto;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.member.port.secondary.MemberDao;
import pl.edu.wit.member.query.MemberFindQuery;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.common.adapter.PageableMapper;
import pl.edu.wit.member.adapter.mapper.MemberMapper;
import pl.edu.wit.member.adapter.model.QMemberDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Repository
@RequiredArgsConstructor
public class MongoMemberDao implements MemberDao {

    private final MongoMemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PageableMapper<MemberDto> pageableMapper;

    @Override
    public String save(MemberDto member) {
        var memberDocument = memberMapper.toDocument(member);
        return memberRepository.save(memberDocument).getId();
    }

    @Override
    public Optional<MemberDto> findOne(MemberFindQuery query) {
        var qMember = QMemberDocument.memberDocument;
        var builder = new BooleanBuilder();
        ofNullable(query.getMemberId()).ifPresent(memberId -> builder.and(qMember.id.eq(memberId)));
        ofNullable(query.getAuthDetailsId()).ifPresent(authDetailsId -> builder.and(qMember.authDetails.id.eq(authDetailsId)));

        return ofNullable(builder.getValue())
                .flatMap(memberRepository::findOne)
                .map(memberMapper::toDto);
    }

    @Override
    public PageSlice<MemberDto> findAll(MemberFindQuery findQuery, PageableParamsQuery pageableQuery) {
        var pageable = pageableMapper.toPageable(pageableQuery);
        var page = ofNullable(buildPredicate(findQuery))
                .map(predicate -> memberRepository.findAll(predicate, pageable))
                .orElseGet(() -> memberRepository.findAll(pageable))
                .map(memberMapper::toDto);
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
                .filter(not(String::isBlank))
                .map(String::toLowerCase)
                .ifPresent(fullName -> builder.and(qMember.name.lower().like(fullName)).or(qMember.surname.lower().like(fullName)));
    }

}
