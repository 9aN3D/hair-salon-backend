package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;

import java.time.LocalDate;

@QueryEmbeddable
record EmbeddableHairdresserDayOverrideId(String hairdresserId, LocalDate date) {

    EmbeddableHairdresserDayOverrideId(HairdresserDayOverrideIdDto id) {
        this(id.hairdresserId(), id.date());
    }

    HairdresserDayOverrideIdDto toDto() {
        return new HairdresserDayOverrideIdDto(hairdresserId, date);
    }

}
