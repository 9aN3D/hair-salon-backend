package pl.edu.wit.hairsalon.hairdresser.command;

import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public record HairdresserCreateCommand(
        String name,
        String surname,
        Set<String> services,
        Map<DayOfWeek, TimeRangeDto> weeklySchedule
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String surname;
        private Set<String> services;
        private Map<DayOfWeek, TimeRangeDto> weeklySchedule;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder services(Set<String> services) {
            this.services = services;
            return this;
        }

        public Builder weeklySchedule(Map<DayOfWeek, TimeRangeDto> weeklySchedule) {
            this.weeklySchedule = weeklySchedule;
            return this;
        }

        public HairdresserCreateCommand build() {
            return new HairdresserCreateCommand(name, surname, services, weeklySchedule);
        }
    }
}

