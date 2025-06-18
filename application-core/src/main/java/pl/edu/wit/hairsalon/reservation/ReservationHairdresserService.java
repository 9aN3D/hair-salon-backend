package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.time.Duration;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

record ReservationHairdresserService(
        String serviceId,
        String name,
        Money price,
        Duration duration
) implements SelfValidator<ReservationHairdresserService> {

    ReservationHairdresserService(ServiceDto arg) {
        this(
                arg.id(),
                arg.name(),
                Money.of(arg.price()),
                Duration.ofMinutes(arg.durationInMinutes())
        );
    }

    @Override
    public ReservationHairdresserService validate() {
        requireNonNull(price, "Hairdresser reservation price must not be null");
        requireNonNull(duration, "Hairdresser reservation duration must not be null");
        validate(new NotBlankString(serviceId), new NotBlankString(name));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationHairdresserService that)) return false;
        return Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serviceId);
    }

    ServiceDto toDto() {
        return ServiceDto.builder()
                .id(serviceId)
                .name(name)
                .price(price.toDto())
                .durationInMinutes(duration.toMinutes())
                .build();
    }

}
