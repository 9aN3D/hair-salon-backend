package pl.edu.wit.hairsalon.reservation.command;

import java.time.LocalDateTime;
import java.util.Set;

public record ReservationCalculateCommand(
        String hairdresserId,
        LocalDateTime startDateTime,
        Set<String> selectedServiceIds
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String hairdresserId;
        private LocalDateTime startDateTime;
        private Set<String> selectedServiceIds;

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder startDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public Builder selectedServiceIds(Set<String> selectedServiceIds) {
            this.selectedServiceIds = selectedServiceIds;
            return this;
        }

        public ReservationCalculateCommand build() {
            return new ReservationCalculateCommand(hairdresserId, startDateTime, selectedServiceIds);
        }
    }
    
}
