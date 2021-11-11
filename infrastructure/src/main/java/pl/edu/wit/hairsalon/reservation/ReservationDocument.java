package pl.edu.wit.hairsalon.reservation;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Builder
@QueryEntity
@Document(value = "Reservation")
@EqualsAndHashCode(of = "id")
class ReservationDocument {

    @Id
    private String id;

}
