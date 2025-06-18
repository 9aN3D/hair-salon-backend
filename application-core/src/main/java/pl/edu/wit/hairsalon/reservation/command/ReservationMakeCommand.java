package pl.edu.wit.hairsalon.reservation.command;

import java.time.LocalDateTime;
import java.util.Set;

public record ReservationMakeCommand(
        String hairdresserId,
        LocalDateTime startDateTime,
        Set<String> selectedServiceIds
) {

    public ReservationCalculateCommand toCalculateCommand() {
        return ReservationCalculateCommand.builder()
                .hairdresserId(hairdresserId)
                .startDateTime(startDateTime)
                .selectedServiceIds(selectedServiceIds)
                .build();
    }

}
