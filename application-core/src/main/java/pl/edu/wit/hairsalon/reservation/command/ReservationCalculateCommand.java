package pl.edu.wit.hairsalon.reservation.command;

import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculateStepDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record ReservationCalculateCommand(
        ReservationCalculateStepDto step,
        LocalDate date,
        Set<String> selectedServiceIds,
        String hairdresserId,
        LocalTime startTime
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ReservationCalculateStepDto step;
        private String hairdresserId;
        private LocalDate date;
        private LocalTime startTime;
        private Set<String> selectedServiceIds;

        public Builder step(ReservationCalculateStepDto step) {
            this.step = step;
            return this;
        }
        
        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder selectedServiceIds(Set<String> selectedServiceIds) {
            this.selectedServiceIds = selectedServiceIds;
            return this;
        }

        public ReservationCalculateCommand build() {
            return new ReservationCalculateCommand(step, date, selectedServiceIds, hairdresserId, startTime);
        }

    }

}
