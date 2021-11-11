package pl.edu.wit.hairsalon.reservation;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@QueryEntity
@Document(value = "Reservation")
@EqualsAndHashCode(of = "id")
class ReservationDocument {

    @Id
    private String id;

    private String memberId;

    private ReservationHairdresserDto hairdresser;

    private DateRangeDto times;

    private List<ServiceDto> selectedServices;

    private BigDecimal totalPrice;

    private LocalDateTime creationDateTime;

}
