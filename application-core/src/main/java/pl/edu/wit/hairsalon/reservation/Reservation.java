package pl.edu.wit.hairsalon.reservation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Reservation implements SelfValidator<Reservation> {

    private final String id;
    private final String memberId;
    private final ReservationHairdresser hairdresser;
    private final DateRange times;
    private final List<ReservationHairdresserService> selectedServices;
    private final BigDecimal totalPrice;
    private final LocalDateTime creationDateTime;

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

}
