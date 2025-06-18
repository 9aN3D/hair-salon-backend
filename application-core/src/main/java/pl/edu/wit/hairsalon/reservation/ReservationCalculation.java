package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.exception.ReservationCalculationException;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static pl.edu.wit.hairsalon.sharedKernel.CollectionHelper.nonNullOrEmpty;

record ReservationCalculation(
        ReservationHairdresser hairdresser,
        DateRange times,
        List<ReservationHairdresserService> selectedServices,
        BigDecimal totalPrice
) implements SelfValidator<ReservationCalculation> {

    ReservationCalculation(HairdresserDto arg, LocalDateTime startDateTime) {
        this(new ReservationHairdresser(arg), new DateRange(startDateTime, null), new ArrayList<>(), ZERO);
    }

    ReservationCalculation addReservationHairdresserServices(List<ServiceDto> args) {
        requireNonNull(args, "Reservation hairdresser services must not be null");
        hairdresser.addAllServices(args);
        return this;
    }

    ReservationCalculation addSelectedServiceIds(Set<String> args) {
        if (nonNullOrEmpty(args)) {
            var serviceIdToHairdresserService = hairdresser.collectServiceIdToService();
            var selectedServicesStreamSupplier = getSelectedServicesStreamSupplier(serviceIdToHairdresserService, args);
            selectedServices.addAll(selectedServicesStreamSupplier.get()
                    .toList());
            return ReservationCalculation.builder()
                    .hairdresser(hairdresser)
                    .times(new DateRange(times.start(), calculateEndDateTime(selectedServicesStreamSupplier)).validate())
                    .selectedServices(selectedServices)
                    .totalPrice(calculateTotalPrice(selectedServicesStreamSupplier))
                    .build();
        }
        return this;
    }

    ReservationCalculation verifyReservedTimes(Function<ScheduledEventFindQuery, Long> countFunction) {
        requireNonNull(countFunction, "Count function must not be null");
        if (times.isNotEmpty()) {
            var findQuery = ScheduledEventFindQuery.of(times.toDto(), hairdresser.id());
            if (countFunction.apply(findQuery) != 0) {
                throw new ReservationCalculationException(
                        format("These appointment times have already reserved, {hairdresserId=%s, times=%s}", hairdresser.id(), times)
                );
            }
        }
        return this;
    }

    ReservationCalculationDto toDto() {
        return ReservationCalculationDto.builder()
                .hairdresser(hairdresser.toDto())
                .times(times.toDto())
                .selectedServices(collectSelectedServices())
                .totalPrice(hasNotSelectedServiceIds() ? null : totalPrice)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationCalculation that)) return false;
        return Objects.equals(hairdresser, that.hairdresser);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hairdresser);
    }

    @Override
    public ReservationCalculation validate() {
        requireNonNull(hairdresser, "Reservation calculation hairdresser must not be null");
        requireNonNull(times, "Reservation calculation times must not be null");
        requireNonNull(times.start(), "Reservation calculation start date time must not be null");
        validate(hairdresser);
        var now = now();
        if (times.start().isBefore(now)) {
            throw new ValidationException(
                    format("Reservation calculation start date time %s is before %s", times.start(), now)
            );
        }
        return this;
    }

    static Builder builder() {
        return new Builder();
    }

    private Supplier<Stream<ReservationHairdresserService>> getSelectedServicesStreamSupplier(Map<String, ReservationHairdresserService> serviceIdToHairdresserService, Set<String> args) {
        return () -> args.stream()
                .map(selectedServiceId -> getHairdresserService(serviceIdToHairdresserService, selectedServiceId));
    }

    private ReservationHairdresserService getHairdresserService(Map<String, ReservationHairdresserService> serviceIdToHairdresserService, String selectedServiceId) {
        return ofNullable(serviceIdToHairdresserService.get(selectedServiceId))
                .orElseThrow(() -> new ReservationCalculationException(
                        format("Selected service by id %s is not found in hairdresser services", selectedServiceId)
                ));
    }

    private LocalDateTime calculateEndDateTime(Supplier<Stream<ReservationHairdresserService>> selectedServicesStreamSupplier) {
        var calculatedTotalDuration = calculateTotalDuration(selectedServicesStreamSupplier);
        return times.start().plusMinutes(calculatedTotalDuration.toMinutes());
    }

    private Duration calculateTotalDuration(Supplier<Stream<ReservationHairdresserService>> selectedServicesStreamSupplier) {
        return selectedServicesStreamSupplier.get()
                .map(ReservationHairdresserService::duration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private BigDecimal calculateTotalPrice(Supplier<Stream<ReservationHairdresserService>> selectedServicesStreamSupplier) {
        return selectedServicesStreamSupplier.get()
                .map(ReservationHairdresserService::price)
                .reduce(Money.ZERO, Money::add)
                .amount();
    }

    private List<ServiceDto> collectSelectedServices() {
        if (hasNotSelectedServiceIds()) {
            return null;
        }
        return selectedServices.stream()
                .map(ReservationHairdresserService::toDto)
                .collect(toList());
    }

    private boolean hasNotSelectedServiceIds() {
        return selectedServices.isEmpty();
    }

    static class Builder {

        private ReservationHairdresser hairdresser;
        private DateRange times;
        private List<ReservationHairdresserService> selectedServices;
        private BigDecimal totalPrice;

        Builder hairdresser(ReservationHairdresser hairdresser) {
            this.hairdresser = hairdresser;
            return this;
        }

        Builder times(DateRange times) {
            this.times = times;
            return this;
        }

        Builder selectedServices(List<ReservationHairdresserService> selectedServices) {
            this.selectedServices = selectedServices;
            return this;
        }

        Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        ReservationCalculation build() {
            return new ReservationCalculation(hairdresser, times, selectedServices, totalPrice);
        }

    }

}
