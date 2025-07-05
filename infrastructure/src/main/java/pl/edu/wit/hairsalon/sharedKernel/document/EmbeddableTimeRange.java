package pl.edu.wit.hairsalon.sharedKernel.document;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.time.LocalTime;

@QueryEmbeddable
public record EmbeddableTimeRange(LocalTime start, LocalTime end) {

    public TimeRangeDto toDto() {
        return new TimeRangeDto(start, end);
    }

}
