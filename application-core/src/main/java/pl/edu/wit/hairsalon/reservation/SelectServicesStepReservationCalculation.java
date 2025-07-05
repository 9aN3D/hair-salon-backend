package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

record SelectServicesStepReservationCalculation(
        ReservationCalculation delegate,
        List<ReservationHairdresserService> availableServices,
        List<ReservationHairdresserService> selectedServices
) implements ReservationCalculation {

    SelectServicesStepReservationCalculation(ReservationCalculation delegate, List<ReservationHairdresserService> availableServices) {
        this(delegate, availableServices, new ArrayList<>());
    }

    @Override
    public ReservationCalculation validate() {
        delegate.validate();
        requireNonNull(delegate, "SelectServicesStepReservationCalculation, delegate must not be null");
        requireNonNull(availableServices, "SelectServicesStepReservationCalculation, availableServices must not be null");
        return this;
    }

    @Override
    public LocalDate date() {
        return delegate.date();
    }

    @Override
    public LocalDateTime now() {
        return delegate.now();
    }
    
    void fullValidate() {
        validate();
        requireNonNull(selectedServices, "SelectServicesStepReservationCalculation, selectedServices must not be null");
        if (selectedServices.isEmpty()) {
            throw new ValidationException("Reservation calculation selected services must not be empty");
        }
    }

    SelectServicesStepReservationCalculation fillSelectedServices(Set<String> selectedServiceIds) {
        selectedServices.addAll(collectSelectedServices(selectedServiceIds));
        return this;
    }

    BigDecimal calculateTotalPrice() {
        return selectedServices.stream()
                .map(ReservationHairdresserService::price)
                .reduce(Money.ZERO, Money::add)
                .amount();
    }

    @Override
    public ReservationCalculationDto.Builder toDtoBuilder() {
        return delegate.toDtoBuilder()
                .availableServices(collectServicesDto(availableServices))
                .selectedServices(collectServicesDto(selectedServices))
                .totalPrice(calculateTotalPrice());
    }

    private List<ReservationHairdresserService> collectSelectedServices(Set<String> selectedServiceIds) {
        if (isNull(selectedServiceIds) || selectedServiceIds.isEmpty()) {
            return new ArrayList<>();
        }
        return availableServices.stream()
                .filter(service -> selectedServiceIds.contains(service.serviceId()))
                .distinct()
                .toList();
    }

    private List<ServiceDto> collectServicesDto(List<ReservationHairdresserService> services) {
        return services.stream()
                .map(ReservationHairdresserService::toDto)
                .collect(toList());
    }

}
