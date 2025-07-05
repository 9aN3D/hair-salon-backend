package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto.ClosedOverrideDto;

record ClosedOverride(HairdresserDayOverrideId id) implements HairdresserDayOverride, SelfValidator<ClosedOverride> {

    ClosedOverride(HairdresserDayOverrideIdDto id) {
        this(new HairdresserDayOverrideId(id));
    }

    ClosedOverride(ClosedOverrideDto closed) {
        this(new HairdresserDayOverrideId(closed.id()));
    }

    @Override
    public ClosedOverride validate() {
        requireNonNull(id, "ClosedOverride, id must not be null");
        return this;
    }

    @Override
    public HairdresserDayOverrideDto toDto() {
        return new ClosedOverrideDto(
                id.toDto()
        );
    }

}
