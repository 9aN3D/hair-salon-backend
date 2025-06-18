package pl.edu.wit.hairsalon.scheduledEvent;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableDateRange;

import java.util.Objects;
import java.util.StringJoiner;

@QueryEntity
@Document(value = "ScheduledEvent")
class ScheduledEventDocument {

    @Id
    private String id;

    private EmbeddableDateRange times;

    @Indexed
    private String hairdresserId;

    private ScheduledEventTypeDto type;

    private String description;

    @Indexed
    private String reservationId;

    ScheduledEventDocument() {
    }

    ScheduledEventDocument(String id, EmbeddableDateRange times, String hairdresserId,
                           ScheduledEventTypeDto type, String description, String reservationId) {
        this.id = id;
        this.times = times;
        this.hairdresserId = hairdresserId;
        this.type = type;
        this.description = description;
        this.reservationId = reservationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmbeddableDateRange getTimes() {
        return times;
    }

    public void setTimes(EmbeddableDateRange times) {
        this.times = times;
    }

    public String getHairdresserId() {
        return hairdresserId;
    }

    public void setHairdresserId(String hairdresserId) {
        this.hairdresserId = hairdresserId;
    }

    public ScheduledEventTypeDto getType() {
        return type;
    }

    public void setType(ScheduledEventTypeDto type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScheduledEventDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ScheduledEventDocument.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("times=" + times)
                .add("hairdresserId='" + hairdresserId + "'")
                .add("type=" + type)
                .add("description='" + description + "'")
                .add("reservationId='" + reservationId + "'")
                .toString();
    }

}
