package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

/**
 * Odpowiedź reprezentująca jedno wydarzenie zaplanowane w kalendarzu.

 *
 * @param id            identyfikator zaplanowanego wydarzenia
 * @param startDate     data i godzina rozpoczęcia
 * @param endDate       data i godzina zakończenia
 * @param hairdresserId identyfikator fryzjera przypisanego do wydarzenia
 *
 * @see ScheduledEventDto
 */
public record ScheduledEventResponse(
        @NotBlank String id,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotBlank String hairdresserId
) {

    /**
     * Tworzy instancję {@link ScheduledEventResponse} na podstawie DTO wydarzenia.
     *
     * @param scheduledEvent źródłowy DTO wydarzenia
     * @return odpowiedź gotowa do zwrócenia z API
     */
    public static ScheduledEventResponse of(ScheduledEventDto scheduledEvent) {
        return builder()
                .id(scheduledEvent.id())
                .startDate(scheduledEvent.times().start())
                .endDate(scheduledEvent.times().end())
                .hairdresserId(scheduledEvent.hairdresserId())
                .build();
    }

    /**
     * Tworzy nową instancję buildera.
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link ScheduledEventResponse}.
     */
    public static final class Builder {

        private String id;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String hairdresserId;

        /** Ustawia identyfikator wydarzenia.
         * 
         * @param id identyfikator
         * @return builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Ustawia datę rozpoczęcia wydarzenia.
         * 
         * @param startDate data rozpoczęcia wydarzenia
         * @return builder
         */
        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        /** Ustawia datę zakończenia wydarzenia.
         * 
         * @param endDate data zakończenia wydarzenia
         * @return builder
         */
        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        /** Ustawia identyfikator fryzjera przypisanego do wydarzenia.
         * 
         * @param hairdresserId identyfikator fryzjera
         * @return builder
         */
        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        /**
         * Buduje nową instancję {@link ScheduledEventResponse}, walidując wymagane pola.
         *
         * @return gotowy obiekt odpowiedzi
         * @throws NullPointerException jeśli którekolwiek z wymaganych pól jest null
         */
        public ScheduledEventResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(startDate, "startDate must not be null");
            requireNonNull(endDate, "endDate must not be null");
            requireNonNull(hairdresserId, "hairdresserId must not be null");
            return new ScheduledEventResponse(id, startDate, endDate, hairdresserId);
        }

    }

}

