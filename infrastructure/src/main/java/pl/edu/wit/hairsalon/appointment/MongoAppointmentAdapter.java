package pl.edu.wit.hairsalon.appointment;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.exception.AppointmentNotFoundException;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoAppointmentAdapter implements AppointmentPort {

    private final MongoAppointmentRepository repository;
    private final AppointmentMapper mapper;

    public MongoAppointmentAdapter(MongoAppointmentRepository repository,
                                   AppointmentMapper appointmentMapper) {
        this.repository = repository;
        this.mapper = appointmentMapper;
    }

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
        return QuerydslPredicateBuilder.create()
                .equals(qAppointment.id, findQuery.appointmentId())
                .equals(qAppointment.hairdresserId, findQuery.hairdresserId())
                .equals(qAppointment.memberId, findQuery.memberId())
                .equals(qAppointment.notification.sent, findQuery.notificationSent())
                .in(qAppointment.status, findQuery.statuses())
                .notIn(qAppointment.status, findQuery.exceptStatuses())
                .includes(qAppointment.times, findQuery.includesTimes())
                .greaterThanEqual(qAppointment.times.start, findQuery.startTimeGreaterThanEqual())
                .lessThan(qAppointment.times.start, findQuery.startTimeLessThan())
                .build();
    }

}
