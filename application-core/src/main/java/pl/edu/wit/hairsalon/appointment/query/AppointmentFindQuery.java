package pl.edu.wit.hairsalon.appointment.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto.RESERVED;
import static pl.edu.wit.hairsalon.sharedkernel.CollectionHelper.nonNullOrEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentFindQuery {

    private DateRangeDto includesTimes;

    private String hairdresserId;

    private Set<AppointmentStatusDto> statuses;

    private String memberId;

    private String appointmentId;

    private LocalDateTime startTimeGreaterThanEqual;

    private LocalDateTime startTimeLessThan;

    private Set<AppointmentStatusDto> orExceptStatuses;

    public static AppointmentFindQuery with(String memberId, String appointmentId) {
        return AppointmentFindQuery.builder()
                .memberId(memberId)
                .appointmentId(appointmentId)
                .build();
    }

    public AppointmentFindQuery withMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public static AppointmentFindQuery with(DateRangeDto times, String hairdresserId, Set<AppointmentStatusDto> statuses) {
        return AppointmentFindQuery.builder()
                .includesTimes(times)
                .hairdresserId(hairdresserId)
                .statuses(statuses)
                .build();
    }

    public static AppointmentFindQuery withReserved(DateRangeDto times, String hairdresserId) {
        return AppointmentFindQuery.builder()
                .includesTimes(times)
                .hairdresserId(hairdresserId)
                .statuses(Set.of(RESERVED))
                .build();
    }

    public void ifIncludesTimesPresent(Consumer<DateRangeDto> action) {
        if (nonNull(includesTimes) && nonNull(includesTimes.getStart()) && nonNull(includesTimes.getEnd())) {
            action.accept(includesTimes);
        }
    }

    public void ifHairdresserIdPresent(Consumer<String> action) {
        if (nonNull(hairdresserId) && !hairdresserId.trim().isBlank()) {
            action.accept(hairdresserId);
        }
    }

    public void ifStatusesPresent(Consumer<Set<AppointmentStatusDto>> action) {
        if (nonNullOrEmpty(statuses)) {
            action.accept(statuses);
        }
    }

    public void ifMemberIdPresent(Consumer<String> action) {
        if (nonNull(memberId) && !memberId.trim().isBlank()) {
            action.accept(memberId);
        }
    }

    public void ifAppointmentIdPresent(Consumer<String> action) {
        if (nonNull(appointmentId) && !appointmentId.trim().isBlank()) {
            action.accept(appointmentId);
        }
    }

    public void ifStartTimeGreaterThanEqualPresent(Consumer<LocalDateTime> action) {
        if (nonNull(startTimeGreaterThanEqual)) {
            action.accept(startTimeGreaterThanEqual);
        }
    }

    public void ifStartTimeLessThanPresent(Consumer<LocalDateTime> action) {
        if (nonNull(startTimeLessThan)) {
            action.accept(startTimeLessThan);
        }
    }

    public void ifOrExceptStatusesPresent(Consumer<Set<AppointmentStatusDto>> action) {
        if (nonNullOrEmpty(orExceptStatuses)) {
            action.accept(orExceptStatuses);
        }
    }

}
