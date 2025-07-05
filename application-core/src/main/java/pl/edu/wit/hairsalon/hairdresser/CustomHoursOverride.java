package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto.CustomHoursOverrideDto;

record CustomHoursOverride(HairdresserDayOverrideId id, TimeRange value) implements HairdresserDayOverride, SelfValidator<CustomHoursOverride> {

    public CustomHoursOverride(HairdresserDayOverrideIdDto id, TimeRangeDto timeRange) {
        this(new HairdresserDayOverrideId(id), new TimeRange(timeRange));
    }

    public CustomHoursOverride(CustomHoursOverrideDto custom) {
        this(custom.id(), custom.timeRange());
    }

    @Override
    public CustomHoursOverride validate() {
        requireNonNull(id, "CustomHoursOverride, id must not be null");
        requireNonNull(value, "CustomHoursOverride, value must not be null");
        validate(value);
        return this;
    }

    @Override
    public HairdresserDayOverrideDto toDto() {
        return new CustomHoursOverrideDto(
                id.toDto(),
                value.toDto()
        );
    }

}
