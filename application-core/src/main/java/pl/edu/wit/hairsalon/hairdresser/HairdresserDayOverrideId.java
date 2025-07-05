package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

record HairdresserDayOverrideId(
        String hairdresserId,
        LocalDate date
) implements SelfValidator<HairdresserDayOverrideId> {

    HairdresserDayOverrideId(HairdresserDayOverrideIdDto id) {
        this(id.hairdresserId(), id.date());
    }

    @Override
    public HairdresserDayOverrideId validate() {
        requireNonNull(hairdresserId, "HairdresserDayOverrideId,  hairdresserId must not be null");
        requireNonNull(date, "HairdresserDayOverrideId, the date must not be null");
        validate(new NotBlankString(hairdresserId));
        return this;
    }

    HairdresserDayOverrideIdDto toDto() {
        return new HairdresserDayOverrideIdDto(hairdresserId, date);
    }

}
