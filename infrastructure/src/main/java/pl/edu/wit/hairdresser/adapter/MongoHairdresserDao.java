package pl.edu.wit.hairdresser.adapter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairdresser.dto.HairdresserDto;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.hairdresser.port.secondary.HairdresserDao;
import pl.edu.wit.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.common.adapter.PageableMapper;
import pl.edu.wit.hairdresser.adapter.mapper.HairdresserMapper;
import pl.edu.wit.hairdresser.adapter.model.QHairdresserDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Repository
@RequiredArgsConstructor
public class MongoHairdresserDao implements HairdresserDao {

    private final MongoHairdresserRepository hairdresserRepository;
    private final HairdresserMapper mapper;
    private final PageableMapper<HairdresserDto> pageableMapper;

    @Override
    public String save(HairdresserDto hairdresser) {
        var hairdresserDocument = mapper.toDocument(hairdresser);
        return hairdresserRepository.save(hairdresserDocument).getId();
    }

    @Override
    public Optional<HairdresserDto> findOne(HairdresserFindQuery query) {
        var qHairdresser = QHairdresserDocument.hairdresserDocument;
        var builder = new BooleanBuilder();
        ofNullable(query.getHairdresserId()).ifPresent(hairdresserId -> builder.and(qHairdresser.id.eq(hairdresserId)));

        return ofNullable(builder.getValue())
                .flatMap(hairdresserRepository::findOne)
                .map(mapper::toDto);
    }

    @Override
    public PageSlice<HairdresserDto> findAll(HairdresserFindQuery findQuery, PageableParamsQuery pageableQuery) {
        var pageable = pageableMapper.toPageable(pageableQuery);
        var page = ofNullable(buildPredicate(findQuery))
                .map(predicate -> hairdresserRepository.findAll(predicate, pageable))
                .orElseGet(() -> hairdresserRepository.findAll(pageable))
                .map(mapper::toDto);
        return pageableMapper.toPageSlice(page);
    }

    private Predicate buildPredicate(HairdresserFindQuery findQuery) {
        var qHairdresser = QHairdresserDocument.hairdresserDocument;
        var builder = new BooleanBuilder();
        buildLikeFullName(findQuery, qHairdresser, builder);
        return builder.getValue();
    }

    private void buildLikeFullName(HairdresserFindQuery findQuery, QHairdresserDocument qHairdresser, BooleanBuilder builder) {
        ofNullable(findQuery.getFullName())
                .map(String::trim)
                .filter(not(String::isBlank))
                .map(String::toLowerCase)
                .ifPresent(fullName -> builder.and(qHairdresser.name.lower().like(fullName)).or(qHairdresser.surname.lower().like(fullName)));
    }

}
