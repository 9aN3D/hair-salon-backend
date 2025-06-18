package pl.edu.wit.hairsalon.reservation;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableDateRange;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@QueryEntity
@Document(value = "Reservation")
class ReservationDocument {

    @Id
    private String id;

    private String memberId;

    private EmbeddableReservationHairdresser hairdresser;

    private EmbeddableDateRange times;

    private List<EmbeddableService> selectedServices;

    private BigDecimal totalPrice;

    private LocalDateTime creationDateTime;

    ReservationDocument() {
    }

    ReservationDocument(String id, String memberId, EmbeddableReservationHairdresser hairdresser, EmbeddableDateRange times,
                        List<EmbeddableService> selectedServices, BigDecimal totalPrice, LocalDateTime creationDateTime) {
        this.id = id;
        this.memberId = memberId;
        this.hairdresser = hairdresser;
        this.times = times;
        this.selectedServices = selectedServices;
        this.totalPrice = totalPrice;
        this.creationDateTime = creationDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public EmbeddableReservationHairdresser getHairdresser() {
        return hairdresser;
    }

    public void setHairdresser(EmbeddableReservationHairdresser hairdresser) {
        this.hairdresser = hairdresser;
    }

    public EmbeddableDateRange getTimes() {
        return times;
    }

    public void setTimes(EmbeddableDateRange times) {
        this.times = times;
    }

    public List<EmbeddableService> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(List<EmbeddableService> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ReservationDocument.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("memberId='" + memberId + "'")
                .add("hairdresser=" + hairdresser)
                .add("times=" + times)
                .add("selectedServices=" + selectedServices)
                .add("totalPrice=" + totalPrice)
                .add("creationDateTime=" + creationDateTime)
                .toString();
    }

}
