package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServicesDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.util.Objects;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

/**
 * Szczegółowa odpowiedź reprezentująca pojedynczą wizytę użytkownika.
 *
 * @param id                 identyfikator wizyty (wymagany, niepusty)
 * @param times              zakres czasowy wizyty (od–do)
 * @param appointmentServices szczegóły dotyczące zamówionych usług
 * @param status             aktualny status wizyty (np. ZAREZERWOWANA, ODWOLANA)
 * @param hairdresser        fryzjer, który będzie wykonywał wybrane usługi w trakcie tej wizyty
 *
 * @see AppointmentServicesDto
 * @see HairdresserResponse
 * @see AppointmentStatusDto
 * @see DateRangeDto
 */
public record AppointmentResponse(
        @NotBlank String id,
        @NotNull DateRangeDto times,
        @NotNull AppointmentServicesDto appointmentServices,
        @NotNull AppointmentStatusDto status,
        @NotNull HairdresserResponse hairdresser
) {

    /**
     * Tworzy builder dla {@link AppointmentResponse}.
     *
     * @return nowy builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link AppointmentResponse}, umożliwia stopniowe tworzenie kompletnego obiektu.
     */
    public static final class Builder {

        private String id;
        private DateRangeDto times;
        private AppointmentServicesDto appointmentServices;
        private AppointmentStatusDto status;
        private HairdresserResponse hairdresser;

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
         * Ustawia listę wybranych usług.
         *
         * @param appointmentServices lista wybranych usług
         * @return builder
         */
        public Builder appointmentServices(AppointmentServicesDto appointmentServices) {
            this.appointmentServices = appointmentServices;
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
         * Ustawia fryzjera, który będzie wykonywał wybrane usługi w trakcie tej wizyty.
         *
         * @param hairdresser fryzjer
         * @return builder
         */
        public Builder hairdresser(HairdresserResponse hairdresser) {
            this.hairdresser = hairdresser;
            return this;
        }

        /**
         * Buduje nową instancję {@link AppointmentResponse}, upewniając się, że wszystkie pola są ustawione.
         *
         * @return nowy obiekt odpowiedzi
         * @throws NullPointerException jeśli którekolwiek z wymaganych pól jest null
         */
        public AppointmentResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(times, "times must not be null");
            requireNonNull(appointmentServices, "appointmentServices must not be null");
            requireNonNull(status, "status must not be null");
            requireNonNull(hairdresser, "hairdresser must not be null");
            return new AppointmentResponse(id, times, appointmentServices, status, hairdresser);
        }

    }

    /**
     * Porównuje obiekty po identyfikatorze wizyty.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    /**
     * HashCode oparty wyłącznie na identyfikatorze wizyty.
     */
    @Override
    public int hashCode() {
        return hash(id);
    }

}

