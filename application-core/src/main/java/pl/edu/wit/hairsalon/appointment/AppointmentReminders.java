package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto.RESERVED;

@RequiredArgsConstructor
class AppointmentReminders {

    private final AppointmentPort appointmentPort;
    private final AppointmentNotificationSender notificationSender;

    long remind(Pageable pageable) {
        var page = appointmentPort.findAll(buildFindQuery(), pageable);
        page.forEach(appointment -> notificationSender.send(appointment.getId()));
        return page.getNumberOfElements();
    }

    private AppointmentFindQuery buildFindQuery() {
        return AppointmentFindQuery.builder()
                .statuses(Set.of(RESERVED))
                .startTimeLessThan(now().plusDays(1))
                .notificationSent(false)
                .build();
    }

}
