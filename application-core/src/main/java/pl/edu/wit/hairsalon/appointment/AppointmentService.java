package pl.edu.wit.hairsalon.appointment;

import pl.edu.wit.hairsalon.appointment.dto.AppointmentServiceDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

record AppointmentService(
        String serviceId,
        String name,
        Money price,
        Duration duration
) implements SelfValidator<AppointmentService> {

    AppointmentService(AppointmentServiceDto arg) {
        this(
                arg.serviceId(),
                arg.name(),
                Money.of(arg.price()),
                Duration.ofMinutes(arg.durationInMinutes())
        );
    }

    AppointmentService(ServiceDto arg) {
        this(
                arg.id(),
                arg.name(),
                Money.of(arg.price()),
                Duration.ofMinutes(arg.durationInMinutes())
        );
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
