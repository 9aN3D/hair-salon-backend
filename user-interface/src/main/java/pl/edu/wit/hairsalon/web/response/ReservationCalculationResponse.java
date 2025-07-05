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

    public ReservationCalculationResponse {
        availableServices = nonNull(availableServices) ? new ArrayList<>(availableServices) : new ArrayList<>();
        selectedServices = nonNull(selectedServices) ? new ArrayList<>(selectedServices) : new ArrayList<>();
        availableStartTimes = nonNull(availableStartTimes) ? new ArrayList<>(availableStartTimes) : new ArrayList<>();
        availableHairdressers = nonNull(availableHairdressers) ? new ArrayList<>(availableHairdressers) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationCalculationResponse that)) return false;
        return Objects.equals(date, that.date) && Objects.equals(memberId, that.memberId) && Objects.equals(calculationTime, that.calculationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, calculationTime, date);
    }

    public static Builder builder() {
        return new Builder();
    }

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

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder calculationTime(LocalDateTime calculationTime) {
            this.calculationTime = calculationTime;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder availableServices(List<ServiceResponse> availableServices) {
            this.availableServices = availableServices;
            return this;
        }

        public Builder selectedServices(List<ServiceResponse> selectedServices) {
            this.selectedServices = selectedServices;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder availableHairdressers(List<HairdresserResponse> availableHairdressers) {
            this.availableHairdressers = availableHairdressers;
            return this;
        }

        public Builder selectedHairdresser(HairdresserResponse selectedHairdresser) {
            this.selectedHairdresser = selectedHairdresser;
            return this;
        }

        public Builder availableStartTimes(List<String> availableStartTimes) {
            this.availableStartTimes = availableStartTimes;
            return this;
        }

        public Builder times(TimeRangeDto times) {
            this.times = times;
            return this;
        }

        public ReservationCalculationResponse build() {
            Objects.requireNonNull(memberId, "memberId must not be null");
            Objects.requireNonNull(date, "date must not be null");
            Objects.requireNonNull(calculationTime, "calculationTime must not be null");
            Objects.requireNonNull(totalPrice, "totalPrice must not be null");
            return new ReservationCalculationResponse(memberId, calculationTime, date, availableServices, selectedServices, totalPrice, availableHairdressers, selectedHairdresser, availableStartTimes, times);
        }

    }

}
