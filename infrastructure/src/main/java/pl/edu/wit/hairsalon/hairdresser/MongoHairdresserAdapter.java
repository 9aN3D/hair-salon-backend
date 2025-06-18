package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.exception.HairdresserNotFoundException;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoHairdresserAdapter implements HairdresserPort {

    private final MongoHairdresserRepository hairdresserRepository;
    private final HairdresserMapper mapper;

    MongoHairdresserAdapter(MongoHairdresserRepository hairdresserRepository, HairdresserMapper mapper) {
        this.hairdresserRepository = hairdresserRepository;
        this.mapper = mapper;
    }

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
        return QuerydslPredicateBuilder.create()
                .equals(qHairdresser.id, findQuery.id())
                .like(List.of(qHairdresser.fullName.name, qHairdresser.fullName.surname), findQuery.fullName())
                .build();
    }

}
