package pl.edu.wit.hairsalon.reservation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.Money;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "serviceId")
class ReservationHairdresserService implements SelfValidator<ReservationHairdresserService> {

    private final String serviceId;
    private final String name;
    private final Money price;
    private final Duration duration;

    ReservationHairdresserService(ServiceDto arg) {
        this(
                arg.getId(),
                arg.getName(),
                Money.of(arg.getPrice()),
                Duration.ofMinutes(arg.getDurationInMinutes())
        );
    }

    ServiceDto toDto() {
        return ServiceDto.builder()
                .id(serviceId)
                .name(name)
                .price(price.toDto())
                .durationInMinutes(duration.toMinutes())
                .build();
    }

    String serviceId() {
        return serviceId;
    }

    Duration duration() {
        return duration;
    }

    Money price() {
        return price;
    }

    @Override
    public ReservationHairdresserService validate() {
        requireNonNull(price, "Hairdresser reservation price must not be null");
        requireNonNull(duration, "Hairdresser reservation duration must not be null");
        validate(new NotBlankString(serviceId), new NotBlankString(name));
        return this;
    }

}
