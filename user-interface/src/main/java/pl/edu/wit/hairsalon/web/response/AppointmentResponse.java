package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServicesDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AppointmentResponse {

    @NotBlank
    String id;

    @NotNull
    DateRangeDto times;

    @NotNull
    AppointmentServicesDto appointmentServices;

    @NotNull
    AppointmentStatusDto status;

    @NotNull
    HairdresserResponse hairdresser;

}
