package pl.edu.wit.hairsalon.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.appointment.command.AppointmentUpdateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;

public interface AppointmentFacade {

    String create(AppointmentCreateCommand command);

    void update(String appointmentId, AppointmentUpdateCommand command);

    AppointmentDto findOne(AppointmentFindQuery findQuery);

    Page<AppointmentDto> findAll(AppointmentFindQuery findQuery, Pageable pageable);

    long count(AppointmentFindQuery findQuery);

    void resign(String memberId, String appointmentId);

    void reminds(Pageable pageable);

}
