package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.exception.HairdresserNotFoundException;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
class MongoHairdresserAdapter implements HairdresserPort {

    private final MongoHairdresserRepository hairdresserRepository;
    private final HairdresserMapper mapper;

    @Override
    public HairdresserDto save(HairdresserDto hairdresser) {
        var hairdresserDocument = mapper.toDocument(hairdresser);
        return mapper.toDto(hairdresserRepository.save(hairdresserDocument));
    }

    @Override
    public Optional<HairdresserDto> findOne(HairdresserFindQuery query) {
        return getOneFromRepository(query);
    }

    @Override
    public HairdresserDto findOneOrThrow(HairdresserFindQuery findQuery) {
        return getOneFromRepository(findQuery)
                .orElseThrow(() -> new HairdresserNotFoundException(
                        format("Hairdresser not found by findQuery: %s", findQuery))
                );
    }

    @Override
    public Page<HairdresserDto> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> hairdresserRepository.findAll(predicate, pageable))
                .orElseGet(() -> hairdresserRepository.findAll(pageable))
                .map(mapper::toDto);
    }

    private Optional<HairdresserDto> getOneFromRepository(HairdresserFindQuery query) {
        return buildPredicate(query)
                .flatMap(hairdresserRepository::findOne)
                .map(mapper::toDto);
    }

    private Optional<Predicate> buildPredicate(HairdresserFindQuery findQuery) {
        var qHairdresser = QHairdresserDocument.hairdresserDocument;
        var builder = new BooleanBuilder();
        findQuery.ifIdPresent(id -> builder.and(qHairdresser.id.eq(id)));
        findQuery.ifFullNamePresent(fullName -> builder
                .and(qHairdresser.fullName.name.like(fullName))
                .or(qHairdresser.fullName.surname.like(fullName)));
        return ofNullable(builder.getValue());
    }

}
