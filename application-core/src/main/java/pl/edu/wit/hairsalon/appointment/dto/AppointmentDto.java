package pl.edu.wit.hairsalon.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
public class AppointmentDto {

    String id;

    String reservationId;

    String memberId;

    DateRangeDto times;

    AppointmentServicesDto services;

    AppointmentStatusDto status;

    LocalDateTime creationDateTime;

    LocalDateTime statusModificationDateTime;

    String hairdresserId;

    AppointmentNotificationDto notification;

}
