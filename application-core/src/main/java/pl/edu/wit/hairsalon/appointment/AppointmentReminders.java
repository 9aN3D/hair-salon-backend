package pl.edu.wit.hairsalon.appointment;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto.RESERVED;

class AppointmentReminders {

    private final AppointmentPort appointmentPort;
    private final AppointmentNotificationSender notificationSender;

    AppointmentReminders(AppointmentPort appointmentPort, AppointmentNotificationSender notificationSender) {
        this.appointmentPort = appointmentPort;
        this.notificationSender = notificationSender;
    }

    long remind(Pageable pageable) {
        var page = appointmentPort.findAll(buildFindQuery(), pageable);
        page.forEach(appointment -> notificationSender.send(appointment.id()));
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
