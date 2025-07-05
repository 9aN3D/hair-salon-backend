package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.exception.ReservationCalculationException;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public record SelectStartTimeStepReservationCalculation(
        SelectHairdresserStepReservationCalculation previousCalculation,
        Optional<TimeRange> times
) implements ReservationCalculation {

    SelectStartTimeStepReservationCalculation(SelectHairdresserStepReservationCalculation previousCalculation) {
        this(previousCalculation, Optional.empty());
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
    public SelectStartTimeStepReservationCalculation validate() {
        previousCalculation.fullValidate();
        requireNonNull(previousCalculation, "SelectStartTimeStepReservationCalculation, previousCalculation must not be null");
        requireNonNull(times, "SelectStartTimeStepReservationCalculation, times must not be null");
        return this;
    }

    @Override
    public ReservationCalculationDto.Builder toDtoBuilder() {
        return previousCalculation.toDtoBuilder()
                .availableStartTimes(previousCalculation.availableHairdresserStartTimes())
                .times(times.map(TimeRange::toDto).orElse(null));
    }

    SelectStartTimeStepReservationCalculation fillSelectedStartTime(LocalTime startTime) {
        if (nonNull(startTime)) {
            return new SelectStartTimeStepReservationCalculation(
                    previousCalculation,
                    Optional.of(TimeRange.of(startTime, prepareEndDate(previousCalculation, startTime)))
            );
        }
        return this;
    }

    void fullValidate() {
        validate();
        if (times.isEmpty()) {
            throw new ValidationException("SelectStartTimeStepReservationCalculation, times must not be empty");
        }
        times.get().validate();
        if (nonNull(times.get().start()) && !previousCalculation.containsTime(times.get().start())) {
            throw new ReservationCalculationException(
                    format("Selected start time is not available for hairdresser, {hairdresserId=%s, date=%s, times=%s}", previousCalculation.hairdresserId(), date(), times)
            );
        }
    }

    SelectStartTimeStepReservationCalculation verifyReservedTimes(Function<ScheduledEventFindQuery, Long> countFunction) {
        requireNonNull(countFunction, "Count function must not be null");
        if (times.isPresent() && times.get().isNotEmpty()) {
            var findQuery = ScheduledEventFindQuery.of(times.get().toDto(date()), previousCalculation.hairdresserId());
            if (countFunction.apply(findQuery) != 0) {
                throw new ReservationCalculationException(
                        format("These appointment times have already reserved, {hairdresserId=%s, times=%s}", previousCalculation.hairdresserId(), times)
                );
            }
        }
        return this;
    }

    private LocalTime prepareEndDate(SelectHairdresserStepReservationCalculation previousCalculation, LocalTime startTime) {
        if (isNull(previousCalculation) || isNull(startTime)) {
            return startTime;
        }
        return startTime.plusMinutes(previousCalculation.calculateTotalDuration().toMinutes());
    }

}
