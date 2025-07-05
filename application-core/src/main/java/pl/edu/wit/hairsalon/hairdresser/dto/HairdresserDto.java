package pl.edu.wit.hairsalon.hairdresser.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public record HairdresserDto(
        String id,
        FullNameDto fullName,
        String photoId,
        Set<String> serviceIds,
        Map<DayOfWeek, TimeRangeDto> weeklySchedule
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private FullNameDto fullName;
        private String photoId;
        private Set<String> serviceIds;
        private Map<DayOfWeek, TimeRangeDto> weeklySchedule;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(FullNameDto fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder photoId(String photoId) {
            this.photoId = photoId;
            return this;
        }

        public Builder serviceIds(Set<String> serviceIds) {
            this.serviceIds = serviceIds;
            return this;
        }

        public Builder weeklySchedule(Map<DayOfWeek, TimeRangeDto> weeklySchedule) {
            this.weeklySchedule = weeklySchedule;
            return this;
        }

        public HairdresserDto build() {
            return new HairdresserDto(id, fullName, photoId, serviceIds, weeklySchedule);
        }

    }

}
