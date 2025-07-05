package pl.edu.wit.hairsalon.reservation.dto;

import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public record ReservationCalculationDto(
        String memberId,
        LocalDate date,
        LocalDateTime calculationTime,
        List<ServiceDto> availableServices,
        List<ServiceDto> selectedServices,
        List<ReservationHairdresserDto> availableHairdressers,
        Optional<ReservationHairdresserDto> selectedHairdresser,
        List<LocalTime> availableStartTimes,
        Optional<TimeRangeDto> times,
        BigDecimal totalPrice
) {

    public DateRangeDto dateTimes() {
        return times.map(value -> value.toDateRangeDto(date))
                .orElseGet(() -> new DateRangeDto(null, null));
    }

    public List<String> formatAvailableStartTimes() {
        return availableStartTimes.stream()
                .map(value -> value.format((DateTimeFormatter.ofPattern("HH:mm"))))
                .toList();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String memberId;
        private LocalDate date;
        private LocalDateTime calculationTime;
        private List<ServiceDto> availableServices;
        private List<ServiceDto> selectedServices;
        private List<ReservationHairdresserDto> availableHairdressers;
        private ReservationHairdresserDto hairdresser;
        private List<LocalTime> availableStartTimes;
        private TimeRangeDto times;
        private BigDecimal totalPrice;

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder calculationTime(LocalDateTime calculationTime) {
            this.calculationTime = calculationTime;
            return this;
        }

        public Builder availableServices(List<ServiceDto> availableServices) {
            this.availableServices = availableServices;
            return this;
        }

        public Builder selectedServices(List<ServiceDto> selectedServices) {
            this.selectedServices = selectedServices;
            return this;
        }

        public Builder availableHairdressers(List<ReservationHairdresserDto> availableHairdressers) {
            this.availableHairdressers = availableHairdressers;
            return this;
        }

        public Builder hairdresser(ReservationHairdresserDto hairdresser) {
            this.hairdresser = hairdresser;
            return this;
        }

        public Builder availableStartTimes(List<LocalTime> availableStartTimes) {
            this.availableStartTimes = availableStartTimes;
            return this;
        }

        public Builder times(TimeRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReservationCalculationDto build() {
            return new ReservationCalculationDto(
                    memberId,
                    date,
                    calculationTime,
                    availableServices,
                    isNull(selectedServices) ? List.of() : selectedServices,
                    isNull(availableHairdressers) ? List.of() : availableHairdressers,
                    ofNullable(hairdresser),
                    isNull(availableStartTimes) ? List.of() : availableStartTimes,
                    ofNullable(times),
                    totalPrice
            );
        }

    }

}
