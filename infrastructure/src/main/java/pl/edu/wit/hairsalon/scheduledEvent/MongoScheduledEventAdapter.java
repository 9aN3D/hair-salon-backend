package pl.edu.wit.hairsalon.scheduledEvent;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.util.List;
import java.util.Optional;

import static java.util.stream.StreamSupport.stream;

@Repository
class MongoScheduledEventAdapter implements ScheduledEventPort {

    private final MongoScheduledEventRepository repository;
    private final ScheduledEventMapper mapper;

    public MongoScheduledEventAdapter(MongoScheduledEventRepository repository, ScheduledEventMapper scheduledEventMapper) {
        this.repository = repository;
        this.mapper = scheduledEventMapper;
    }

    @Override
    public ScheduledEventDto save(ScheduledEventDto scheduledEvent) {
        var scheduledEventDocument = mapper.toDocument(scheduledEvent);
        return mapper.toDto(repository.save(scheduledEventDocument));
    }

    @Override
    public void deleteByReservationId(String reservationId) {
        repository.deleteByReservationId(reservationId);
    }

    @Override
    public List<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery) {
        return stream(
                buildPredicate(findQuery)
                        .map(repository::findAll)
                        .orElseGet(repository::findAll)
                        .spliterator(),
                false)
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Page<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> repository.findAll(predicate, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(mapper::toDto);
    }

    @Override
    public long count(ScheduledEventFindQuery findQuery) {
        return buildPredicate(findQuery)
                .map(repository::count)
                .orElse(0L);
    }

    private Optional<Predicate> buildPredicate(ScheduledEventFindQuery findQuery) {
        var qScheduledEvent = QScheduledEventDocument.scheduledEventDocument;
        return QuerydslPredicateBuilder.create()
                .equals(qScheduledEvent.hairdresserId, findQuery.hairdresserId())
                .equals(qScheduledEvent.reservationId, findQuery.reservationId())
                .includes(qScheduledEvent.times, new DateRangeDto(findQuery.startDateTime(), findQuery.endDateTime()))
                .overlaps(qScheduledEvent.times, findQuery.overlapsTimes())
                .build();
    }

}
