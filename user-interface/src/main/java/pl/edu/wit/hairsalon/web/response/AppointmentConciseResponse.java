package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServiceDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AppointmentConciseResponse {

    @NotBlank
    String id;

    @NotNull
    DateRangeDto times;

    @NotNull
    List<String> appointmentServiceNames;

    @NotNull
    AppointmentStatusDto status;

    public static AppointmentConciseResponse of(AppointmentDto appointment) {
        return AppointmentConciseResponse.builder()
                .id(appointment.getId())
                .times(appointment.getTimes())
                .appointmentServiceNames(appointment.getServices()
                        .getServices()
                        .stream()
                        .map(AppointmentServiceDto::getName)
                        .collect(toList()))
                .status(appointment.getStatus())
                .build();
    }

}
