package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServiceDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

/**
 * Skrócona odpowiedź reprezentująca podstawowe informacje o wizycie.
 *
 * @param id                      identyfikator wizyty (wymagany, niepusty)
 * @param times                   zakres czasowy wizyty (początek i koniec)
 * @param appointmentServiceNames lista nazw usług powiązanych z wizytą
 * @param status                  status wizyty (np. ZAREZERWOWANA, ODWOLANA)
 *
 * @see AppointmentDto
 * @see AppointmentStatusDto
 * @see DateRangeDto
 */
public record AppointmentConciseResponse(
        @NotBlank String id,
        @NotNull DateRangeDto times,
        @NotNull List<String> appointmentServiceNames,
        @NotNull AppointmentStatusDto status
) {

    /**
     * Tworzy instancję {@link AppointmentConciseResponse} na podstawie obiektu {@link AppointmentDto}.
     *
     * @param appointment DTO wizyty
     * @return nowa instancja odpowiedzi
     */
    public static AppointmentConciseResponse of(AppointmentDto appointment) {
        return builder()
                .id(appointment.id())
                .times(appointment.times())
                .appointmentServiceNames(appointment.services()
                        .services()
                        .stream()
                        .map(AppointmentServiceDto::name)
                        .collect(Collectors.toList()))
                .status(appointment.status())
                .build();
    }

    /**
     * Tworzy instancję buildera dla {@link AppointmentConciseResponse}.
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link AppointmentConciseResponse}.
     * Zapewnia walidację wymaganych pól w metodzie {@code build()}.
     */
    public static final class Builder {

        private String id;
        private DateRangeDto times;
        private List<String> appointmentServiceNames;
        private AppointmentStatusDto status;

        /**
         * Ustawia identyfikator wizyty.
         *
         * @param id identyfikator wizyty
         * @return builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Ustawia zakres czasowy wizyty.
         *
         * @param times zakres czasowy wizyty
         * @return builder
         */
        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        /**
         * Ustawia listę nazw usług powiązanych z wizytą.
         *
         * @param appointmentServiceNames lista nazw usług
         * @return builder
         */
        public Builder appointmentServiceNames(List<String> appointmentServiceNames) {
            this.appointmentServiceNames = appointmentServiceNames;
            return this;
        }

        /**
         * Ustawia status wizyty.
         *
         * @param status status wizyty
         * @return builder
         */
        public Builder status(AppointmentStatusDto status) {
            this.status = status;
            return this;
        }

        /**
         * Tworzy nową instancję {@link AppointmentConciseResponse}, upewniając się,
         * że wszystkie pola wymagane są obecne.
         *
         * @return nowy obiekt odpowiedzi
         * @throws NullPointerException jeśli któreś z pól jest null
         */
        public AppointmentConciseResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(times, "times must not be null");
            requireNonNull(appointmentServiceNames, "appointmentServiceNames must not be null");
            requireNonNull(status, "status must not be null");
            return new AppointmentConciseResponse(id, times, appointmentServiceNames, status);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentConciseResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

}

