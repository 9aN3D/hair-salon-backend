package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.hairdresser.exception.HairdresserDayOverrideNotFoundException;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.time.LocalDate;
import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoHairdresserDayOverrideAdapter implements HairdresserDayOverridePort {

    private final MongoHairdresserDayOverrideRepository hairdresserDayOverrideRepository;
    private final HairdresserDayOverrideMapper mapper;

    public MongoHairdresserDayOverrideAdapter(MongoHairdresserDayOverrideRepository hairdresserDayOverrideRepository,
                                              HairdresserDayOverrideMapper hairdresserDayOverrideMapper) {
        this.hairdresserDayOverrideRepository = hairdresserDayOverrideRepository;
        this.mapper = hairdresserDayOverrideMapper;
    }

    @Override
    public HairdresserDayOverrideDto save(HairdresserDayOverrideDto dto) {
        var hairdresserDayOverrideDocument = mapper.toDocument(dto);
        return mapper.toDto(hairdresserDayOverrideRepository.save(hairdresserDayOverrideDocument));
    }

    @Override
    public Optional<HairdresserDayOverrideDto> findOne(HairdresserDayOverrideIdDto id) {
        return hairdresserDayOverrideRepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public HairdresserDayOverrideDto findOneOrThrow(HairdresserDayOverrideIdDto id) {
        return hairdresserDayOverrideRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new HairdresserDayOverrideNotFoundException(
                        format("HairdresserDayOverride not found by id: %s", id)
                ));
    }

    @Override
    public long count(HairdresserDayOverrideIdDto id) {
        return buildPredicate(id.hairdresserId(), id.date())
                .map(hairdresserDayOverrideRepository::count)
                .orElse(0L);
    }

    private Optional<Predicate> buildPredicate(String hairdresserId, LocalDate date) {
        var qHairdresserDayOverride = QHairdresserDayOverrideDocument.hairdresserDayOverrideDocument;
        return QuerydslPredicateBuilder.create()
                .equals(qHairdresserDayOverride.id.hairdresserId, hairdresserId)
                .equals(qHairdresserDayOverride.id.date, date)
                .build();
    }

}
