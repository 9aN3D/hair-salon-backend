package pl.edu.wit.hairsalon.appointment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.appointment.command.AppointmentUpdateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;

class LoggableAppointmentFacade implements AppointmentFacade {

    private final Logger log;
    private final AppointmentFacade delegate;

    LoggableAppointmentFacade(AppointmentFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableAppointmentFacade.class);
        this.delegate = delegate;
    }

    @Override
    public String create(AppointmentCreateCommand command) {
        log.trace("Creating appointment, command: {}", command);
        var id = delegate.create(command);
        log.info("Created appointment, command: {}, id: {}", command, id);
        return id;
    }

    @Override
    public void update(String appointmentId, AppointmentUpdateCommand command) {
        log.trace("Updating appointment, id: {}, command: {}", appointmentId, command);
        delegate.update(appointmentId, command);
        log.info("Updated appointment, id: {}, command: {}", appointmentId, command);
    }

    @Override
    public AppointmentDto findOne(AppointmentFindQuery findQuery) {
        log.trace("Getting appointment, findQuery: {}", findQuery);
        var dto = delegate.findOne(findQuery);
        log.info("Got appointment, dto: {}", dto);
        return dto;
    }

    @Override
    public Page<AppointmentDto> findAll(AppointmentFindQuery findQuery, Pageable pageable) {
        log.trace("Searching appointments, findQuery: {}, pageable: {}", findQuery, pageable);
        var page = delegate.findAll(findQuery, pageable);
        log.info("Searched appointments, numberOfElements: {}", page.getNumberOfElements());
        return page;
    }

    @Override
    public long count(AppointmentFindQuery findQuery) {
        log.trace("Counting appointments, findQuery: {}", findQuery);
        var count = delegate.count(findQuery);
        log.info("Counted appointments, result: {}", count);
        return count;
    }

    @Override
    public void resign(String memberId, String appointmentId) {
        log.trace("Resigning appointment, memberId: {}, appointmentId: {}", memberId, appointmentId);
        delegate.resign(memberId, appointmentId);
        log.info("Resigned appointment, memberId: {}, appointmentId: {}", memberId, appointmentId);
    }

    @Override
    public void reminds(Pageable pageable) {
        log.trace("Reminding appointments, pageable: {}", pageable);
        delegate.reminds(pageable);
        log.info("Reminded appointments, pageable: {}", pageable);
    }

}

