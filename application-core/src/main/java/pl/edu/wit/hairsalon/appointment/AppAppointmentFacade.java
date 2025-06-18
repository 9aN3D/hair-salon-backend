package pl.edu.wit.hairsalon.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.appointment.command.AppointmentUpdateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;

import static java.util.Objects.requireNonNull;

class AppAppointmentFacade implements AppointmentFacade {

    private final AppointmentPort appointmentPort;
    private final AppointmentCreator creator;
    private final AppointmentUpdater updater;
    private final AppointmentResignation resignation;
    private final AppointmentReminders reminders;

    AppAppointmentFacade(AppointmentPort appointmentPort,
                         AppointmentCreator creator,
                         AppointmentUpdater updater,
                         AppointmentResignation resignation,
                         AppointmentReminders reminders) {
        this.appointmentPort = appointmentPort;
        this.creator = creator;
        this.updater = updater;
        this.resignation = resignation;
        this.reminders = reminders;
    }

    @Override
    public String create(AppointmentCreateCommand command) {
        requireNonNull(command, "Appointment create command must not be null");
        var createdAppointment = creator.create(command);
        return createdAppointment.id();
    }

    @Override
    public void update(String appointmentId, AppointmentUpdateCommand command) {
        requireNonNull(appointmentId, "Appointment id must not be null");
        requireNonNull(command, "Appointment update command must not be null");
        updater.update(appointmentId, command);
    }

    @Override
    public AppointmentDto findOne(AppointmentFindQuery findQuery) {
        requireNonNull(findQuery, "Appointment find query must not be null");
        return appointmentPort.findOneOrThrow(findQuery);
    }

    @Override
    public Page<AppointmentDto> findAll(AppointmentFindQuery findQuery, Pageable pageable) {
        requireNonNull(findQuery, "Appointment find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        return appointmentPort.findAll(findQuery, pageable);
    }

    @Override
    public long count(AppointmentFindQuery findQuery) {
        requireNonNull(findQuery, "Appointment find query must not be null");
        return appointmentPort.count(findQuery);
    }

    @Override
    public void resign(String memberId, String appointmentId) {
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(appointmentId, "Appointment id must not be null");
        resignation.resign(memberId, appointmentId);
    }

    @Override
    public void reminds(Pageable pageable) {
        reminders.remind(pageable);
    }

}

