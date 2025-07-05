package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

record HairdresserSchedule(
        TimeRange workHours,
        List<TimeRange> existingEvents,
        Duration minServiceDuration,
        Duration gapDuration,
        Duration interval
) implements SelfValidator<HairdresserSchedule> {

    public static WorkHoursStep builder() {
        return new Builder();
    }

    @Override
    public HairdresserSchedule validate() {
        requireNonNull(workHours, "HairdresserSchedule, workStart must not be null");
        requireNonNull(existingEvents, "HairdresserSchedule, existingEvents must not be null");
        requireNonNull(minServiceDuration, "HairdresserSchedule, minServiceDuration must not be null");
        requireNonNull(gapDuration, "HairdresserSchedule, gapDuration must not be null");
        requireNonNull(interval, "HairdresserSchedule, interval must not be null");
        validate(workHours);
        return this;
    }

    List<LocalTime> availableSlots() {
        List<LocalTime> available = new ArrayList<>();
        var time = workHours.start();
        var workEnd = workHours.end();

        while (!time.plus(minServiceDuration).isAfter(workEnd)) {
            var candidate = TimeRange.of(time, time.plus(minServiceDuration));
            if (isNotAnyOverlaps(candidate)) {
                available.add(time);
            }
            time = time.plus(interval);
        }
        return available;
    }

    // Metoda obliczająca dostępny czas
    public List<Duration> availableTimeDurations() {
        List<Duration> results = new ArrayList<>();
        var time = workHours.start();
        var workEnd = workHours.end();
        // Sprawdzanie dostępnych przedziałów przed i po wydarzeniach
        for (var event : existingEvents) {
            // Dostępny czas przed wydarzeniem uwzględniając przerwę
            var beforeEvent = Duration.between(time, event.start().minus(gapDuration));
            if (!beforeEvent.isNegative()) {
                results.add(beforeEvent); // Dodajemy przedział przed wydarzeniem
            }
            // Czas po wydarzeniu z uwzględnieniem przerwy
            time = event.end().plus(gapDuration);
        }
        // Dostępny czas po ostatnim wydarzeniu
        var afterLastEvent = Duration.between(time, workEnd);
        if (!afterLastEvent.isNegative()) {
            results.add(afterLastEvent); // Dodajemy przedział po ostatnim wydarzeniu
        }
        return results;
    }

    private boolean isNotAnyOverlaps(TimeRange candidate) {
        return existingEvents.stream().noneMatch(event ->
                candidate.expandBy(gapDuration).overlaps(event.expandBy(gapDuration))
        );
    }

    interface WorkHoursStep {

        ExistingEventsStep workHours(TimeRange workHours);

    }

    interface ExistingEventsStep {

        MinServiceDurationStep existingEvents(List<TimeRange> existingEvents);

    }

    interface MinServiceDurationStep {

        GapDurationStep minServiceDuration(Duration minServiceDuration);

    }

    interface GapDurationStep {

        IntervalStep gapDuration(Duration gapDuration);

    }

    interface IntervalStep {

        BuildStep interval(Duration interval);

    }

    interface BuildStep {

        HairdresserSchedule build();

    }

    static final class Builder implements WorkHoursStep, ExistingEventsStep, MinServiceDurationStep, GapDurationStep, IntervalStep, BuildStep {

        private TimeRange workHours;
        private List<TimeRange> existingEvents;
        private Duration minServiceDuration;
        private Duration gapDuration;
        private Duration interval;

        @Override
        public ExistingEventsStep workHours(TimeRange workHours) {
            this.workHours = workHours;
            return this;
        }

        @Override
        public MinServiceDurationStep existingEvents(List<TimeRange> existingEvents) {
            this.existingEvents = existingEvents;
            return this;
        }

        @Override
        public GapDurationStep minServiceDuration(Duration minServiceDuration) {
            this.minServiceDuration = minServiceDuration;
            return this;
        }

        @Override
        public IntervalStep gapDuration(Duration gapDuration) {
            this.gapDuration = gapDuration;
            return this;
        }

        @Override
        public BuildStep interval(Duration interval) {
            this.interval = interval;
            return this;
        }

        @Override
        public HairdresserSchedule build() {
            return new HairdresserSchedule(workHours, existingEvents, minServiceDuration, gapDuration, interval);
        }

    }

}
