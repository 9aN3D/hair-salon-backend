package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;

import java.util.Optional;

public interface HairdresserDayOverridePort {

    HairdresserDayOverrideDto save(HairdresserDayOverrideDto dto);

    Optional<HairdresserDayOverrideDto> findOne(HairdresserDayOverrideIdDto id);
    
    HairdresserDayOverrideDto findOneOrThrow(HairdresserDayOverrideIdDto id);

    long count(HairdresserDayOverrideIdDto id);

    default boolean exists(HairdresserDayOverrideIdDto id) {
        return count(id) > 0;
    }

}
