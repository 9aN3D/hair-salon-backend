package pl.edu.wit.hairsalon.web.response;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
public class ScheduledEventResponse {

    @NotBlank
    String id;

    @NotNull
    LocalDateTime startDate;

    @NotNull
    LocalDateTime endDate;

    @NotBlank
    String hairdresserId;

    public static ScheduledEventResponse of(ScheduledEventDto scheduledEvent) {
        return ScheduledEventResponse.builder()
                .id(scheduledEvent.getId())
                .startDate(scheduledEvent.getTimes().getStart())
                .endDate(scheduledEvent.getTimes().getEnd())
                .hairdresserId(scheduledEvent.getHairdresserId())
                .build();
    }

}
