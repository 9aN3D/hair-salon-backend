package pl.edu.wit.hairsalon.appointment.dto;

public record AppointmentNotificationDto(
        AppointmentNotificationNameDto name,
        boolean sent
) {

}
