package pl.edu.wit.hairsalon.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AppointmentNotificationDto {

    AppointmentNotificationNameDto name;

    boolean sent;

}
