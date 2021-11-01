package pl.edu.wit.hairsalon.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.exception.MemberNotFoundException;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
class MongoMemberAdapter implements MemberPort {

    private final MongoMemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public String save(MemberDto member) {
        var memberDocument = memberMapper.toDocument(member);
        return memberRepository.save(memberDocument).getId();
    }

    @Override
    public Optional<MemberDto> findOne(MemberFindQuery query) {
        return getOneFromRepository(query);
    }

    @Override
    public MemberDto findOneOrThrow(MemberFindQuery findQuery) {
        return getOneFromRepository(findQuery)
                .orElseThrow(() -> new MemberNotFoundException(
                        format("Member not found by findQuery: %s", findQuery))
                );
    }

    @Override
    public Page<MemberDto> findAll(MemberFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> memberRepository.findAll(predicate, pageable))
                .orElseGet(() -> memberRepository.findAll(pageable))
                .map(memberMapper::toDto);
    }

    private Optional<MemberDto> getOneFromRepository(MemberFindQuery query) {
        return buildPredicate(query)
                .flatMap(memberRepository::findOne)
                .map(memberMapper::toDto);
    }

    private Optional<Predicate> buildPredicate(MemberFindQuery findQuery) {
        var qMember = QMemberDocument.memberDocument;
        var builder = new BooleanBuilder();
        findQuery.ifIdPresent(id -> builder.and(qMember.id.eq(id)));
        findQuery.ifPhonePresent(phone -> builder.and(qMember.contact.phone.like(phone)));
        findQuery.ifFullNamePresent(fullName -> builder
                .and(qMember.fullName.name.like(fullName))
                .or(qMember.fullName.surname.like(fullName)));
        return ofNullable(builder.getValue());
    }

}
