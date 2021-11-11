package pl.edu.wit.hairsalon.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;

public interface AppointmentPort {

    AppointmentDto save(AppointmentDto appointment);

    AppointmentDto findOneOrThrow(String appointmentId);

    Page<AppointmentDto> findAll(AppointmentFindQuery findQuery, Pageable pageable);

    long count(AppointmentFindQuery findQuery);

}
