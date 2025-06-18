package pl.edu.wit.hairsalon.appointment;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentNotificationNameDto;

@QueryEmbeddable
record EmbeddableAppointmentNotification(
        AppointmentNotificationNameDto name,
        boolean sent
) {

}
