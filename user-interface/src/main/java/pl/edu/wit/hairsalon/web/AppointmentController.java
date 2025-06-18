package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;

@RestController
@SecurityRequirement(name = "hair-salon-API")
@RequestMapping(value = "/api/v1/admin/appointments")
class AppointmentController {

    private final AppointmentFacade appointmentFacade;

    AppointmentController(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    @GetMapping(value = "/reminds")
    void remindAppointments(@NotNull Pageable pageable) {
        appointmentFacade.reminds(pageable);
    }

}
