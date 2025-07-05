package pl.edu.wit.hairsalon.hairdresser.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import static pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto.ClosedOverrideDto;
import static pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto.CustomHoursOverrideDto;

public sealed interface HairdresserDayOverrideDto permits ClosedOverrideDto, CustomHoursOverrideDto {

    HairdresserDayOverrideIdDto id();

    record ClosedOverrideDto(HairdresserDayOverrideIdDto id) implements HairdresserDayOverrideDto {

    }

    record CustomHoursOverrideDto(HairdresserDayOverrideIdDto id, TimeRangeDto timeRange) implements HairdresserDayOverrideDto {

    }

}