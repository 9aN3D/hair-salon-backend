package pl.edu.wit.hairsalon.scheduledEvent.command;

import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

public record ScheduledEventCreateCommand(
        DateRangeDto times,
        ScheduledEventTypeDto type,
        String hairdresserId,
        String reservationId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private DateRangeDto times;
        private ScheduledEventTypeDto type;
        private String hairdresserId;
        private String reservationId;

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder type(ScheduledEventTypeDto type) {
            this.type = type;
            return this;
        }

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder reservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public ScheduledEventCreateCommand build() {
            return new ScheduledEventCreateCommand(times, type, hairdresserId, reservationId);
        }

    }

}
