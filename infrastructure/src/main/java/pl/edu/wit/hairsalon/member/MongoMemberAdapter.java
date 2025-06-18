package pl.edu.wit.hairsalon.member;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.exception.MemberNotFoundException;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoMemberAdapter implements MemberPort {

    private final MongoMemberRepository memberRepository;
    private final MemberMapper memberMapper;

    MongoMemberAdapter(MongoMemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

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

    @Override
    public boolean exist(MemberFindQuery findQuery) {
        return getOneFromRepository(findQuery).isPresent();
    }

    private Optional<MemberDto> getOneFromRepository(MemberFindQuery query) {
        return buildPredicate(query)
                .flatMap(memberRepository::findOne)
                .map(memberMapper::toDto);
    }

    private Optional<Predicate> buildPredicate(MemberFindQuery findQuery) {
        var qMember = QMemberDocument.memberDocument;
        return QuerydslPredicateBuilder.create()
                .equals(qMember.id, findQuery.id())
                .like(qMember.contact.phone, findQuery.phone())
                .like(List.of(qMember.fullName.name, qMember.fullName.surname), findQuery.fullName())
                .build();
    }

}
