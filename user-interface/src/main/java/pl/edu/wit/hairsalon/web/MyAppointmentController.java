package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.sharedkernel.dto.Identity;
import pl.edu.wit.hairsalon.web.adapter.AppointmentResponseAdapter;
import pl.edu.wit.hairsalon.web.response.AppointmentConciseResponse;
import pl.edu.wit.hairsalon.web.response.AppointmentResponse;
import pl.edu.wit.hairsalon.web.response.LinkAddingGoogleCalendarEventResponse;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "hair-salon-API")
@RequestMapping(value = "/api/v1/me/appointments")
class MyAppointmentController {

    private final AppointmentResponseAdapter appointmentResponseAdapter;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @ResponseBody
    Page<AppointmentConciseResponse> findAll(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull AppointmentFindQuery findQuery, Pageable pageable) {
        return appointmentResponseAdapter.findAll(identity.getId(), findQuery, pageable);
    }

    @GetMapping(value = "/{appointmentId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @ResponseBody
    AppointmentResponse findOne(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @PathVariable String appointmentId) {
        return appointmentResponseAdapter.findOne(identity.getId(), appointmentId);
    }

    @PutMapping(value = "/{appointmentId}/resign")
    @ResponseStatus(NO_CONTENT)
    void resign(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @PathVariable String appointmentId) {
        appointmentResponseAdapter.resign(identity.getId(), appointmentId);
    }

    @GetMapping(value = "/{appointmentId}/GOOGLE/calendar/event/links", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @ResponseBody
    LinkAddingGoogleCalendarEventResponse getLinkAddingGoogleCalendarEvent(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @PathVariable String appointmentId) {
        return appointmentResponseAdapter.getLinkAddingGoogleCalendarEvent(identity.getId(), appointmentId);
    }

}
