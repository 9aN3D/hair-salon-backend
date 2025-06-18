package pl.edu.wit.hairsalon.scheduledEvent.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

public record ScheduledEventDto(
        String id,
        DateRangeDto times,
        String hairdresserId,
        ScheduledEventTypeDto type,
        String description,
        String reservationId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private DateRangeDto times;
        private String hairdresserId;
        private ScheduledEventTypeDto type;
        private String description;
        private String reservationId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder type(ScheduledEventTypeDto type) {
            this.type = type;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder reservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public ScheduledEventDto build() {
            return new ScheduledEventDto(id, times, hairdresserId, type, description, reservationId);
        }
    }
}

