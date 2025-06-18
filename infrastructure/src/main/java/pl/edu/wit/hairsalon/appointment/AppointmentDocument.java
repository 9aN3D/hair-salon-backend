package pl.edu.wit.hairsalon.appointment;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableDateRange;

import java.time.LocalDateTime;
import java.util.Objects;

@QueryEntity
@Document(value = "Appointment")
class AppointmentDocument {

    @Id
    private String id;
    private String reservationId;
    private String memberId;
    private EmbeddableDateRange times;
    private EmbeddableAppointmentServices services;
    private AppointmentStatusDto status;
    private LocalDateTime creationDateTime;
    private LocalDateTime statusModificationDateTime;
    private String hairdresserId;
    private EmbeddableAppointmentNotification notification;

    public AppointmentDocument() {
    }

    public AppointmentDocument(String id, String reservationId, String memberId, EmbeddableDateRange times,
                               EmbeddableAppointmentServices services, AppointmentStatusDto status,
                               LocalDateTime creationDateTime, LocalDateTime statusModificationDateTime,
                               String hairdresserId, EmbeddableAppointmentNotification notification) {
        this.id = id;
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.times = times;
        this.services = services;
        this.status = status;
        this.creationDateTime = creationDateTime;
        this.statusModificationDateTime = statusModificationDateTime;
        this.hairdresserId = hairdresserId;
        this.notification = notification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public EmbeddableDateRange getTimes() {
        return times;
    }

    public void setTimes(EmbeddableDateRange times) {
        this.times = times;
    }

    public EmbeddableAppointmentServices getServices() {
        return services;
    }

    public void setServices(EmbeddableAppointmentServices services) {
        this.services = services;
    }

    public AppointmentStatusDto getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatusDto status) {
        this.status = status;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getStatusModificationDateTime() {
        return statusModificationDateTime;
    }

    public void setStatusModificationDateTime(LocalDateTime statusModificationDateTime) {
        this.statusModificationDateTime = statusModificationDateTime;
    }

    public String getHairdresserId() {
        return hairdresserId;
    }

    public void setHairdresserId(String hairdresserId) {
        this.hairdresserId = hairdresserId;
    }

    public EmbeddableAppointmentNotification getNotification() {
        return notification;
    }

    public void setNotification(EmbeddableAppointmentNotification notification) {
        this.notification = notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentDocument)) return false;
        AppointmentDocument that = (AppointmentDocument) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private String reservationId;
        private String memberId;
        private EmbeddableDateRange times;
        private EmbeddableAppointmentServices services;
        private AppointmentStatusDto status;
        private LocalDateTime creationDateTime;
        private LocalDateTime statusModificationDateTime;
        private String hairdresserId;
        private EmbeddableAppointmentNotification notification;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder reservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        Builder times(EmbeddableDateRange times) {
            this.times = times;
            return this;
        }

        Builder services(EmbeddableAppointmentServices services) {
            this.services = services;
            return this;
        }

        Builder status(AppointmentStatusDto status) {
            this.status = status;
            return this;
        }

        Builder creationDateTime(LocalDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        Builder statusModificationDateTime(LocalDateTime statusModificationDateTime) {
            this.statusModificationDateTime = statusModificationDateTime;
            return this;
        }

        Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        Builder notification(EmbeddableAppointmentNotification notification) {
            this.notification = notification;
            return this;
        }

        AppointmentDocument build() {
            return new AppointmentDocument(id, reservationId, memberId, times, services, status,
                    creationDateTime, statusModificationDateTime, hairdresserId, notification);
        }

    }

}

