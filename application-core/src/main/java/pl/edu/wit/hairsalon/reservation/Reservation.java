package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

record Reservation(
        String id,
        String memberId,
        ReservationHairdresser hairdresser,
        DateRange times,
        List<ReservationHairdresserService> selectedServices,
        BigDecimal totalPrice,
        LocalDateTime creationDateTime
) implements SelfValidator<Reservation> {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reservation that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public Reservation validate() {
        requireNonNull(hairdresser, "Reservation hairdresser must not be null");
        requireNonNull(times, "Reservation times must not be null");
        requireNonNull(selectedServices, "Reservation selected services must not be null");
        requireNonNull(totalPrice, "Reservation total price must not be null");
        requireNonNull(creationDateTime, "Reservation creation date time must not be null");
        validate(new NotBlankString(id), new NotBlankString(memberId), hairdresser, times);
        if (selectedServices.size() > 3) {
            throw new ValidationException("Limit of selected reservation services cannot more than 3");
        }
        return this;
    }

    ReservationDto toDto() {
        return ReservationDto.builder()
                .id(id)
                .memberId(memberId)
                .hairdresser(hairdresser.toDto())
                .times(times.toDto())
                .selectedServices(collectSelectedServices())
                .totalPrice(totalPrice)
                .creationDateTime(creationDateTime)
                .build();
    }

    void publishCreateEvent(Consumer<Object> action) {
        action.accept(new ReservationMadeEvent(toDto()));
    }

    private List<ServiceDto> collectSelectedServices() {
        return selectedServices.stream()
                .map(ReservationHairdresserService::toDto)
                .collect(toList());
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private String memberId;
        private ReservationHairdresser hairdresser;
        private DateRange times;
        private List<ReservationHairdresserService> selectedServices;
        private BigDecimal totalPrice;
        private LocalDateTime creationDateTime;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        Builder hairdresser(ReservationHairdresser hairdresser) {
            this.hairdresser = hairdresser;
            return this;
        }

        Builder times(DateRange times) {
            this.times = times;
            return this;
        }

        Builder selectedServices(List<ReservationHairdresserService> selectedServices) {
            this.selectedServices = selectedServices;
            return this;
        }

        Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        Builder creationDateTime(LocalDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        Reservation build() {
            return new Reservation(id, memberId, hairdresser, times, selectedServices, totalPrice, creationDateTime);
        }

    }

}
