package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.appointment.command.AppointmentUpdateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppAppointmentFacade implements AppointmentFacade {

    private final AppointmentPort appointmentPort;
    private final AppointmentCreator creator;
    private final AppointmentUpdater updater;
    private final AppointmentResignation resignation;
    private final AppointmentReminders reminders;

    @Override
    public String create(AppointmentCreateCommand command) {
        log.trace("Creating appointment {command: {}}", command);
        requireNonNull(command, "Appointment create command must not be null");
        var createdAppointment = creator.create(command);
        log.info("Created appointment {createdAppointment: {}}", createdAppointment);
        return createdAppointment.getId();
    }

    @Override
    public void update(String appointmentId, AppointmentUpdateCommand command) {
        log.trace("Updating appointment {id: {}, command: {}}", appointmentId, command);
        requireNonNull(appointmentId, "Appointment id must not be null");
        requireNonNull(command, "Appointment create command must not be null");
        var updatedAppointment = updater.update(appointmentId, command);
        log.info("Updated appointment {dto: {}}", updatedAppointment);
    }

    @Override
    public AppointmentDto findOne(AppointmentFindQuery findQuery) {
        log.trace("Getting appointment {findQuery: {}}", findQuery);
        requireNonNull(findQuery, "Appointment find query must not be null");
        var appointment = appointmentPort.findOneOrThrow(findQuery);
        log.info("Got appointment {dto: {}}", appointment);
        return appointment;
    }

    @Override
    public Page<AppointmentDto> findAll(AppointmentFindQuery findQuery, Pageable pageable) {
        log.trace("Searching appointments {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Appointment find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = appointmentPort.findAll(findQuery, pageable);
        log.info("Searched appointments {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

    @Override
    public long count(AppointmentFindQuery findQuery) {
        log.trace("Counting appointments {findQuery: {}}", findQuery);
        requireNonNull(findQuery, "Appointment find query must not be null");
        var appointmentCount = appointmentPort.count(findQuery);
        log.info("Counted appointments {result: {}}", appointmentCount);
        return appointmentCount;
    }

    @Override
    public void resign(String memberId, String appointmentId) {
        log.trace("Resigning appointment {memberId: {}, appointmentId: {}}", memberId, appointmentId);
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(appointmentId, "Appointment Id must not be null");
        var resignedAppointment = resignation.resign(memberId, appointmentId);
        log.info("Resigned appointment {result: {}}", resignedAppointment);
    }

    @Override
    public void reminds(Pageable pageable) {
        log.trace("Reminding appointments {pageable: {}}", pageable);
        var appointmentCount = reminders.remind(pageable);
        log.info("Reminded appointments {result: {}}", appointmentCount);
    }

}
