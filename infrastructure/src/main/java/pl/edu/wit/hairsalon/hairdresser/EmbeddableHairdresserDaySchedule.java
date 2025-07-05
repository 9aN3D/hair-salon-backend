package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableTimeRange;

import java.time.DayOfWeek;

@QueryEmbeddable
public record EmbeddableHairdresserDaySchedule(DayOfWeek dayOfWeek, EmbeddableTimeRange timeRange) {

}
