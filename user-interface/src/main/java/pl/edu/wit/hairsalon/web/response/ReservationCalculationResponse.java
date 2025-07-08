package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Odpowiedź z wynikiem kalkulacji możliwej rezerwacji.
 * <p>
 * Reprezentuje wynik logiki, która — na podstawie wybranego dnia, usług i użytkownika —
 * określa dostępne opcje rezerwacji: możliwych fryzjerów, godziny rozpoczęcia, sumaryczną cenę itp.
 * </p>
 *
 * <p>Używana głównie w kreatorze rezerwacji na frontendzie — np. przy wyborze dnia, usług lub fryzjera.</p>
 *
 * @param memberId              identyfikator użytkownika, dla którego dokonywana jest kalkulacja
 * @param calculationTime       znacznik czasowy wykonania kalkulacji
 * @param date                  dzień, którego dotyczy rezerwacja
 * @param availableServices     lista dostępnych usług na wskazany dzień (uwzględnia grafik fryzjerów)
 * @param selectedServices      usługi wybrane przez użytkownika w ramach tej kalkulacji
 * @param totalPrice            łączna cena wybranych usług
 * @param availableHairdressers lista fryzjerów dostępnych do wykonania wskazanych usług w tym dniu
 * @param selectedHairdresser   fryzjer wybrany przez użytkownika (może być {@code null})
 * @param availableStartTimes   lista możliwych godzin rozpoczęcia (np. ["10:15", "10:50", "11:40"])
 * @param times                 zaproponowany przedział czasu (może być {@code null}, jeśli nieustalony)
 *
 * @see ServiceResponse
 * @see HairdresserResponse
 * @see TimeRangeDto
 */
public record ReservationCalculationResponse(
        @NotNull String memberId,
        @NotNull LocalDateTime calculationTime,
        @NotNull LocalDate date,
        List<ServiceResponse> availableServices,
        List<ServiceResponse> selectedServices,
        @NotNull BigDecimal totalPrice,
        List<HairdresserResponse> availableHairdressers,
        HairdresserResponse selectedHairdresser,
        List<String> availableStartTimes,
        TimeRangeDto times
) {

    /**
     * Tworzy defensywne kopie list, aby zagwarantować niemutowalność kolekcji.
     */
    public ReservationCalculationResponse {
        availableServices = nonNull(availableServices) ? new ArrayList<>(availableServices) : new ArrayList<>();
        selectedServices = nonNull(selectedServices) ? new ArrayList<>(selectedServices) : new ArrayList<>();
        availableStartTimes = nonNull(availableStartTimes) ? new ArrayList<>(availableStartTimes) : new ArrayList<>();
        availableHairdressers = nonNull(availableHairdressers) ? new ArrayList<>(availableHairdressers) : new ArrayList<>();
    }

    /**
     * Porównuje obiekty wyłącznie po {@code memberId}, {@code date} i {@code calculationTime}.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationCalculationResponse that)) return false;
        return Objects.equals(date, that.date) && Objects.equals(memberId, that.memberId) && Objects.equals(calculationTime, that.calculationTime);
    }

    /**
     * HashCode wyliczany na podstawie {@code memberId}, {@code date} i {@code calculationTime}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(memberId, calculationTime, date);
    }

    /**
     * Tworzy builder do zbudowania instancji {@link ReservationCalculationResponse}.
     *
     * @return nowy builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder do konstruowania obiektu {@link ReservationCalculationResponse}.
     */
    public static final class Builder {

        private String memberId;
        private LocalDateTime calculationTime;
        private LocalDate date;
        private List<ServiceResponse> availableServices;
        private List<ServiceResponse> selectedServices;
        private BigDecimal totalPrice;
        private List<HairdresserResponse> availableHairdressers;
        private HairdresserResponse selectedHairdresser;
        private List<String> availableStartTimes;
        private TimeRangeDto times;

        /** Ustawia identyfikator użytkownika 
         * 
         * @param memberId identyfikator
         * @return builder
         */
        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        /** Ustawia czas kalkulacji 
         *
         * @param calculationTime czas kalkulacji
         * @return builder 
         */
        public Builder calculationTime(LocalDateTime calculationTime) {
            this.calculationTime = calculationTime;
            return this;
        }

        /** Ustawia dzień rezerwacji
         *
         * @param date dzień rezerwacji
         * @return builder
         */
        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        /** Ustawia listę dostępnych usług
         * 
         * @param availableServices lista dostępnych usług
         * @return builder
         */
        public Builder availableServices(List<ServiceResponse> availableServices) {
            this.availableServices = availableServices;
            return this;
        }

        /** Ustawia listę usług wybranych przez użytkownika
         * 
         * @param selectedServices lista usług wybranych przez użytkownika
         * @return builder
         */
        public Builder selectedServices(List<ServiceResponse> selectedServices) {
            this.selectedServices = selectedServices;
            return this;
        }

        /** Ustawia łączną cenę usług
         * 
         * @param totalPrice łączną cena usług
         * @return builder
         */
        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        /** Ustawia listę dostępnych fryzjerów
         * 
         * @param availableHairdressers lista dostępnych fryzjerów
         * @return builder
         */
        public Builder availableHairdressers(List<HairdresserResponse> availableHairdressers) {
            this.availableHairdressers = availableHairdressers;
            return this;
        }

        /** Ustawia fryzjera wybranego przez użytkownika
         * 
         * @param selectedHairdresser fryzjer wybrany przez użytkownika
         * @return builder
         */
        public Builder selectedHairdresser(HairdresserResponse selectedHairdresser) {
            this.selectedHairdresser = selectedHairdresser;
            return this;
        }

        /** Ustawia możliwe godziny rozpoczęcia rezerwacji
         * 
         * @param availableStartTimes dostępne godziny rozpoczęcia rezerwacji
         * @return builder
         */
        public Builder availableStartTimes(List<String> availableStartTimes) {
            this.availableStartTimes = availableStartTimes;
            return this;
        }

        /** Ustawia czas trwania
         * 
         * @param times czas trwania
         * @return builder
         */
        public Builder times(TimeRangeDto times) {
            this.times = times;
            return this;
        }

        /**
         * Buduje instancję {@link ReservationCalculationResponse}, weryfikując wymagane pola.
         *
         * @return nowa instancja odpowiedzi kalkulacyjnej
         */
        public ReservationCalculationResponse build() {
            Objects.requireNonNull(memberId, "memberId must not be null");
            Objects.requireNonNull(date, "date must not be null");
            Objects.requireNonNull(calculationTime, "calculationTime must not be null");
            Objects.requireNonNull(totalPrice, "totalPrice must not be null");
            return new ReservationCalculationResponse(memberId, calculationTime, date, availableServices, selectedServices, totalPrice, availableHairdressers, selectedHairdresser, availableStartTimes, times);
        }

    }

}
