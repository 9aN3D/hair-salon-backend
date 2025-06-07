package pl.edu.wit.hairsalon.scheduledEvent;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.Indexed;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

@Data
@Builder
@QueryEntity
@Document(value = "ScheduledEvent")
@EqualsAndHashCode(of = "id")
class ScheduledEventDocument {

    @Id
    private String id;

    private DateRangeDto times;

    @Indexed
    private String hairdresserId;

    private ScheduledEventTypeDto type;

    private String description;

    @Indexed
    private String reservationId;

}
