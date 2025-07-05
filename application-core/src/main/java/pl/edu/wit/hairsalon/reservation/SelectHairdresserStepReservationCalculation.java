package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public record SelectHairdresserStepReservationCalculation(
        SelectServicesStepReservationCalculation previousCalculation,
        List<ReservationHairdresser> availableHairdressers,
        Optional<ReservationHairdresser> hairdresser
) implements ReservationCalculation {

    SelectHairdresserStepReservationCalculation(SelectServicesStepReservationCalculation previousCalculation, List<ReservationHairdresser> availableHairdressers) {
        this(previousCalculation, availableHairdressers, Optional.empty());
    }

    @Override
    public LocalDate date() {
        return previousCalculation.date();
    }

    @Override
    public LocalDateTime now() {
        return previousCalculation.now();
    }

    @Override
    public SelectHairdresserStepReservationCalculation validate() {
        previousCalculation.fullValidate();
        requireNonNull(previousCalculation, "SelectHairdresserStepReservationCalculation, previousCalculation must not be null");
        requireNonNull(hairdresser, "SelectHairdresserStepReservationCalculation, hairdresser must not be null");
        return this;
    }

    @Override
    public ReservationCalculationDto.Builder toDtoBuilder() {
        return previousCalculation.toDtoBuilder()
                .availableHairdressers(collectHairdressersDto())
                .hairdresser(hairdresser.map(ReservationHairdresser::toDto).orElse(null));
    }

    void fullValidate() {
        validate();
        if (hairdresser.isEmpty()) {
            throw new ValidationException("SelectHairdresserStepReservationCalculation, hairdresser must not be empty");
        }
        hairdresser.get().validate();
    }

    SelectHairdresserStepReservationCalculation fillSelectedHairdresser(String hairdresserId) {
        if (nonNull(hairdresserId)) {
            return new SelectHairdresserStepReservationCalculation(
                    previousCalculation,
                    availableHairdressers,
                    findFirstAvailableHairdresser(hairdresserId)
            );
        }
        return this;
    }

    String hairdresserId() {
        return hairdresser.map(ReservationHairdresser::id)
                .orElse(null);
    }

    Duration calculateTotalDuration() {
        return previousCalculation.selectedServices()
                .stream()
                .map(ReservationHairdresserService::duration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    List<LocalTime> availableHairdresserStartTimes() {
        return hairdresser.map(value -> value.getAvailableStartTimesForDate(date()))
                .orElseGet(List::of);
    }

    boolean containsTime(LocalTime time) {
        return hairdresser.map(value -> value.getAvailableStartTimesForDate(date()).contains(time))
                .orElse(false);
    }

    private Optional<ReservationHairdresser> findFirstAvailableHairdresser(String hairdresserId) {
        return availableHairdressers.stream()
                .filter(value -> value.hasSameId(hairdresserId))
                .findFirst();
    }

    private List<ReservationHairdresserDto> collectHairdressersDto() {
        return availableHairdressers.stream()
                .map(ReservationHairdresser::toDto)
                .collect(toList());
    }

}
