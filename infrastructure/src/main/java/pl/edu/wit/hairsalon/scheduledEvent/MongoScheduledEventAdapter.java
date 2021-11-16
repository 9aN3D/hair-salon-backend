package pl.edu.wit.hairsalon.scheduledEvent;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.QDateRangeDto;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
class MongoScheduledEventAdapter implements ScheduledEventPort {

    private final MongoScheduledEventRepository repository;
    private final ScheduledEventMapper mapper;

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
        var builder = new BooleanBuilder();
        findQuery.ifIncludesStartAndEndDateTimesPresent(includesTime -> builder.and(includes(qScheduledEvent.times, includesTime)));
        findQuery.ifOverlapsTimesPresent(includesTime -> builder.and(overlaps(qScheduledEvent.times, includesTime)));
        findQuery.ifHairdresserIdPresent(hairdresserId -> builder.and(qScheduledEvent.hairdresserId.eq(hairdresserId)));
        findQuery.ifReservationIdPresent(reservationId -> builder.and(qScheduledEvent.reservationId.eq(reservationId)));
        return ofNullable(builder.getValue());
    }

    private BooleanExpression includes(QDateRangeDto qDateRangeDto, DateRangeDto arg) {
        return qDateRangeDto.start.between(arg.getStart(), arg.getEnd()).and(qDateRangeDto.end.between(arg.getStart(), arg.getEnd()));
    }

    public BooleanExpression overlaps(QDateRangeDto qDateRangeDto, DateRangeDto arg) {
        return qDateRangeDto.start.between(arg.getStart(), arg.getEnd())
                .or(qDateRangeDto.end.between(arg.getStart(), arg.getEnd()))
                .or(includes(qDateRangeDto, arg));
    }

}
