package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.sharedKernel.dto.Identity;
import pl.edu.wit.hairsalon.web.adapter.ReservationResponseAdapter;
import pl.edu.wit.hairsalon.web.response.ReservationCalculationResponse;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "hair-salon-API")
@RequestMapping(value = "/api/v1/reservations")
class ReservationController {

    private final ReservationResponseAdapter reservationAdapter;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    void make(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull @RequestBody ReservationMakeCommand command) {
        reservationAdapter.make(identity.getId(), command);
    }

    @GetMapping(value = "/calculations", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    ReservationCalculationResponse calculate(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull ReservationCalculateCommand command) {
        return reservationAdapter.calculate(identity.getId(), command);
    }

}
