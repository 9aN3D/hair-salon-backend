package pl.edu.wit.hairsalon.appointment;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentNotificationDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServicesDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@QueryEntity
@Document(value = "Appointment")
@EqualsAndHashCode(of = "id")
class AppointmentDocument {

    @Id
    private String id;

    private String reservationId;

    private String memberId;

    private DateRangeDto times;

    private AppointmentServicesDto services;

    private AppointmentStatusDto status;

    private LocalDateTime creationDateTime;

    private LocalDateTime statusModificationDateTime;

    private String hairdresserId;

    private AppointmentNotificationDto notification;

}
