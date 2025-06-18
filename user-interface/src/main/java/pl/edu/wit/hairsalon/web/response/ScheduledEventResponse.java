package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public record ScheduledEventResponse(
        @NotBlank String id,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotBlank String hairdresserId
) {

    public static ScheduledEventResponse of(ScheduledEventDto scheduledEvent) {
        return builder()
                .id(scheduledEvent.id())
                .startDate(scheduledEvent.times().start())
                .endDate(scheduledEvent.times().end())
                .hairdresserId(scheduledEvent.hairdresserId())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String hairdresserId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public ScheduledEventResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(startDate, "startDate must not be null");
            requireNonNull(endDate, "endDate must not be null");
            requireNonNull(hairdresserId, "hairdresserId must not be null");

            return new ScheduledEventResponse(id, startDate, endDate, hairdresserId);
        }

    }

}

