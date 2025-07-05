package pl.edu.wit.hairsalon.reservation.command;

import java.time.LocalDateTime;
import java.util.Set;

import static pl.edu.wit.hairsalon.reservation.dto.ReservationCalculateStepDto.SUMMARY;

public record ReservationMakeCommand(
        String hairdresserId,
        LocalDateTime startDateTime,
        Set<String> selectedServiceIds
) {

    public ReservationCalculateCommand toCalculateCommand() {
        return ReservationCalculateCommand.builder()
                .step(SUMMARY)
                .hairdresserId(hairdresserId)
                .date(startDateTime.toLocalDate())
                .startTime(startDateTime.toLocalTime())
                .selectedServiceIds(selectedServiceIds)
                .build();
    }

}
