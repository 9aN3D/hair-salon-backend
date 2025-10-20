package pl.edu.wit.hairsalon.hairdresser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange.of;

class HairdresserScheduleTest {

    @Test
    @DisplayName("Generuje pełną siatkę godzin przy braku przeszkód")
    void shouldReturnAllSlotsWhenNoEvents() {
        var schedule = HairdresserSchedule.builder()
                .workHours(of(LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .existingEvents(emptyList())
                .minServiceDuration(Duration.ofMinutes(15))
                .gapDuration(Duration.ofMinutes(10))
                .interval(Duration.ofMinutes(20))
                .build()
                .validate();

        var slots = schedule.availableSlots();
        var availableTimeDurations = schedule.availableTimeDurations();
        
        assertEquals(List.of(
                LocalTime.of(9, 0),
                LocalTime.of(9, 20),
                LocalTime.of(9, 40)
        ), slots);
        assertEquals(1, availableTimeDurations.size());
        assertEquals(schedule.workHours().duration(), availableTimeDurations.getFirst());
    }

    @Test
    @DisplayName("Brak miejsca na najkrótszą usługę")
    void shouldReturnEmptySlotsWhenMinServiceTooLong() {
        var schedule = HairdresserSchedule.builder()
                .workHours(of(LocalTime.of(9, 0), LocalTime.of(9, 15)))
                .existingEvents(emptyList())
                .minServiceDuration(Duration.ofMinutes(20))
                .gapDuration(Duration.ofMinutes(10))
                .interval(Duration.ofMinutes(5))
                .build()
                .validate();
        
        var availableTimeDurations = schedule.availableTimeDurations();
        
        assertTrue(schedule.availableSlots().isEmpty());
        assertEquals(1, availableTimeDurations.size());
        assertEquals(schedule.workHours().duration(), availableTimeDurations.getFirst());
    }

    @Test
    @DisplayName("Uwzględnienie eventu + buforu (gap) przy blokadzie slotów")
    void shouldSkipSlotsOverlappingWithEventsAndGap() {
        var event = of(LocalTime.of(9, 10), LocalTime.of(9, 30));
        var schedule = HairdresserSchedule.builder()
                .workHours(of(LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .existingEvents(List.of(event))
                .minServiceDuration(Duration.ofMinutes(15))
                .gapDuration(Duration.ofMinutes(5))
                .interval(Duration.ofMinutes(15))
                .build()
                .validate();

        var slots = schedule.availableSlots();
        var availableTimeDurations = schedule.availableTimeDurations();
        
        assertEquals(List.of(
                LocalTime.of(9, 45) // only slot that doesn’t overlap with 9:10–9:30 ± 5min
        ), slots);
        assertEquals(2, availableTimeDurations.size());
        assertEquals(5, availableTimeDurations.getFirst().toMinutes()); //9:00 - 9:10
        assertEquals(25, availableTimeDurations.getLast().toMinutes()); //9:30 + 5 - 10:00
    }

    @Test
    @DisplayName("Walidacja pól wymaganych")
    void shouldThrowExceptionWhenAnyFieldIsNull() {
        assertThrows(NullPointerException.class, () -> HairdresserSchedule.builder()
                .workHours(null)
                .existingEvents(emptyList())
                .minServiceDuration(Duration.ofMinutes(15))
                .gapDuration(Duration.ofMinutes(5))
                .interval(Duration.ofMinutes(15))
                .build()
                .validate());

        assertThrows(NullPointerException.class, () -> HairdresserSchedule.builder()
                .workHours(of(LocalTime.of(8, 0), LocalTime.of(9, 0)))
                .existingEvents(null)
                .minServiceDuration(Duration.ofMinutes(15))
                .gapDuration(Duration.ofMinutes(5))
                .interval(Duration.ofMinutes(15))
                .build()
                .validate());

        assertThrows(NullPointerException.class, () -> HairdresserSchedule.builder()
                .workHours(of(LocalTime.of(8, 0), LocalTime.of(9, 0)))
                .existingEvents(emptyList())
                .minServiceDuration(null)
                .gapDuration(Duration.ofMinutes(5))
                .interval(Duration.ofMinutes(15))
                .build()
                .validate());

        assertThrows(NullPointerException.class, () -> HairdresserSchedule.builder()
                .workHours(of(LocalTime.of(8, 0), LocalTime.of(9, 0)))
                .existingEvents(emptyList())
                .minServiceDuration(Duration.ofMinutes(15))
                .gapDuration(null)
                .interval(Duration.ofMinutes(15))
                .build()
                .validate());

        assertThrows(NullPointerException.class, () -> HairdresserSchedule.builder()
                .workHours(of(LocalTime.of(8, 0), LocalTime.of(9, 0)))
                .existingEvents(emptyList())
                .minServiceDuration(Duration.ofMinutes(15))
                .gapDuration(Duration.ofMinutes(5))
                .interval(null)
                .build()
                .validate());
    }

}