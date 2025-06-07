package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;

import jakarta.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "hair-salon-API")
@RequestMapping(value = "/api/v1/admin/appointments")
class AppointmentController {

    private final AppointmentFacade appointmentFacade;

    @GetMapping(value = "/reminds")
    void remindAppointments(@NotNull Pageable pageable) {
        appointmentFacade.reminds(pageable);
    }

}
