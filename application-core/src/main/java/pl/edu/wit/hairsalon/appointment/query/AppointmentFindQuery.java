package pl.edu.wit.hairsalon.appointment.query;

import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.time.LocalDateTime;
import java.util.Set;

import static pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto.RESERVED;

public record AppointmentFindQuery(
        DateRangeDto includesTimes,
        String hairdresserId,
        Set<AppointmentStatusDto> statuses,
        String memberId,
        String appointmentId,
        LocalDateTime startTimeGreaterThanEqual,
        LocalDateTime startTimeLessThan,
        Set<AppointmentStatusDto> exceptStatuses,
        Boolean notificationSent
) {

    public static AppointmentFindQuery with(String appointmentId) {
        return AppointmentFindQuery.builder()
                .appointmentId(appointmentId)
                .build();
    }

    public static AppointmentFindQuery with(String memberId, String appointmentId) {
        return AppointmentFindQuery.builder()
                .memberId(memberId)
                .appointmentId(appointmentId)
                .build();
    }

    public AppointmentFindQuery withMemberId(String memberId) {
        return AppointmentFindQuery.builder()
                .memberId(memberId)
                .includesTimes(includesTimes)
                .hairdresserId(hairdresserId)
                .statuses(statuses)
                .appointmentId(appointmentId)
                .exceptStatuses(exceptStatuses)
                .startTimeGreaterThanEqual(startTimeGreaterThanEqual)
                .startTimeLessThan(startTimeLessThan)
                .notificationSent(notificationSent)
                .build();
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private DateRangeDto includesTimes;
        private String hairdresserId;
        private Set<AppointmentStatusDto> statuses;
        private String memberId;
        private String appointmentId;
        private LocalDateTime startTimeGreaterThanEqual;
        private LocalDateTime startTimeLessThan;
        private Set<AppointmentStatusDto> exceptStatuses;
        private Boolean notificationSent;

        public Builder includesTimes(DateRangeDto includesTimes) {
            this.includesTimes = includesTimes;
            return this;
        }

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder statuses(Set<AppointmentStatusDto> statuses) {
            this.statuses = statuses;
            return this;
        }

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder appointmentId(String appointmentId) {
            this.appointmentId = appointmentId;
            return this;
        }

        public Builder startTimeGreaterThanEqual(LocalDateTime startTimeGreaterThanEqual) {
            this.startTimeGreaterThanEqual = startTimeGreaterThanEqual;
            return this;
        }

        public Builder startTimeLessThan(LocalDateTime startTimeLessThan) {
            this.startTimeLessThan = startTimeLessThan;
            return this;
        }

        public Builder exceptStatuses(Set<AppointmentStatusDto> exceptStatuses) {
            this.exceptStatuses = exceptStatuses;
            return this;
        }

        public Builder notificationSent(Boolean notificationSent) {
            this.notificationSent = notificationSent;
            return this;
        }

        public AppointmentFindQuery build() {
            return new AppointmentFindQuery(
                    includesTimes,
                    hairdresserId,
                    statuses,
                    memberId,
                    appointmentId,
                    startTimeGreaterThanEqual,
                    startTimeLessThan,
                    exceptStatuses,
                    notificationSent
            );
        }

    }

}
