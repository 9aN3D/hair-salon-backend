package pl.edu.wit.hairsalon.appointment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServiceDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.Money;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "serviceId")
class AppointmentService implements SelfValidator<AppointmentService> {

    private final String serviceId;
    private final String name;
    private final Money price;
    private final Duration duration;

    AppointmentService(ServiceDto arg) {
        this(
                arg.getId(),
                arg.getName(),
                Money.of(arg.getPrice()),
                Duration.ofMinutes(arg.getDurationInMinutes())
        );
    }

    String name() {
        return name;
    }

    Money price() {
        return price;
    }

    Duration duration() {
        return duration;
    }

    AppointmentServiceDto toDto() {
        return AppointmentServiceDto.builder()
                .serviceId(serviceId)
                .name(name)
                .price(price.toDto())
                .durationInMinutes(duration().toMinutes())
                .build();
    }

    @Override
    public AppointmentService validate() {
        validate(new NotBlankString(serviceId), new NotBlankString(name));
        requireNonNull(price, "Appointment service price must not be null");
        requireNonNull(duration, "Appointment service duration must not be null");
        if (price.hasNotPositiveAmount()) {
            throw new ValidationException("Appointment service price must not be negative");
        }
        if (duration.isNegative()) {
            throw new ValidationException("Appointment service duration must not be negative");
        }
        return this;
    }

}
