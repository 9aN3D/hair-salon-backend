package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.time.DayOfWeek;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

class HairdresserCreator {

    private final IdGenerator idGenerator;
    private final HairdresserPort hairdresserPort;

    HairdresserCreator(IdGenerator idGenerator, HairdresserPort hairdresserPort) {
        this.idGenerator = idGenerator;
        this.hairdresserPort = hairdresserPort;
    }

    HairdresserDto create(HairdresserCreateCommand command) {
        var newHairdresser = buildHairdresser(command).validate();
        return hairdresserPort.save(newHairdresser.toDto());
    }

    private Hairdresser buildHairdresser(HairdresserCreateCommand command) {
        return Hairdresser.builder()
                .id(idGenerator.generate())
                .fullName(new FullName(command.name(), command.surname()))
                .services(command.services())
                .weeklySchedule(toWeeklySchedule(command))
                .build();
    }

    private HairdresserWeeklySchedule toWeeklySchedule(HairdresserCreateCommand command) {
        return new HairdresserWeeklySchedule(command.weeklySchedule()
                .entrySet()
                .stream()
                .map(entry -> toDaySchedule(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet()));
    }

    private HairdresserDaySchedule toDaySchedule(DayOfWeek dayOfWeek, TimeRangeDto timeRange) {
        return new HairdresserDaySchedule(
                dayOfWeek,
                isNull(timeRange) ? null : new TimeRange(timeRange)
        );
    }

}
