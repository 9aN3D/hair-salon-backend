package pl.edu.wit.hairsalon.appointment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.exception.AppointmentNotFoundException;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.QDateRangeDto;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
class MongoAppointmentAdapter implements AppointmentPort {

    private final MongoAppointmentRepository repository;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentDto save(AppointmentDto appointment) {
        var appointmentDocument = mapper.toDocument(appointment);
        return mapper.toDto(repository.save(appointmentDocument));
    }

    @Override
    public AppointmentDto findOneOrThrow(AppointmentFindQuery findQuery) {
        return buildPredicate(findQuery)
                .flatMap(repository::findOne)
                .map(mapper::toDto)
                .orElseThrow(() -> new AppointmentNotFoundException(
                        format("Appointment not found by findQuery: %s", findQuery)
                ));
    }

    @Override
    public Page<AppointmentDto> findAll(AppointmentFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> repository.findAll(predicate, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(mapper::toDto);
    }

    @Override
    public long count(AppointmentFindQuery findQuery) {
        return buildPredicate(findQuery)
                .map(repository::count)
                .orElse(0L);
    }

    private Optional<Predicate> buildPredicate(AppointmentFindQuery findQuery) {
        var qAppointment = QAppointmentDocument.appointmentDocument;
        var builder = new BooleanBuilder();
        findQuery.ifIncludesTimesPresent(includesTime -> builder.and(includes(qAppointment.times, includesTime)));
        findQuery.ifHairdresserIdPresent(hairdresserId -> builder.and(qAppointment.hairdresserId.eq(hairdresserId)));
        findQuery.ifStatusesPresent(statuses -> builder.and(qAppointment.status.in(statuses)));
        findQuery.ifMemberIdPresent(memberId -> builder.and(qAppointment.memberId.eq(memberId)));
        findQuery.ifAppointmentIdPresent(appointmentId -> builder.and(qAppointment.id.eq(appointmentId)));
        findQuery.ifStartTimeGreaterThanEqualPresent(startDateTime -> builder.and(qAppointment.times.start.goe(startDateTime)));
        findQuery.ifStartTimeLessThanPresent(startDateTime -> builder.and(qAppointment.times.start.lt(startDateTime)));
        findQuery.ifOrExceptStatusesPresent(statuses -> builder.or(qAppointment.status.notIn(statuses)));
        findQuery.ifNotificationSentPresent(notificationSent -> builder.and(qAppointment.notification.sent.eq(notificationSent)));
        return ofNullable(builder.getValue());
    }

    private BooleanExpression includes(QDateRangeDto qDateRangeDto, DateRangeDto arg) {
        return qDateRangeDto.start.between(arg.getStart(), arg.getEnd()).and(qDateRangeDto.end.between(arg.getStart(), arg.getEnd()));
    }

}
