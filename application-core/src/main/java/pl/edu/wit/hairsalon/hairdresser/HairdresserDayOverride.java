package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto;

import static pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto.ClosedOverrideDto;
import static pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto.CustomHoursOverrideDto;

sealed interface HairdresserDayOverride permits ClosedOverride, CustomHoursOverride {

    HairdresserDayOverrideId id();

    HairdresserDayOverride validate();

    HairdresserDayOverrideDto toDto();

    static HairdresserDayOverride of(HairdresserDayOverrideDto dto) {
        return switch (dto) {
            case ClosedOverrideDto closed -> new ClosedOverride(closed);
            case CustomHoursOverrideDto custom -> new CustomHoursOverride(custom);
        };
    }

}
