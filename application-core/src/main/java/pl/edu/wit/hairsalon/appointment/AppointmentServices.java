package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServiceDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServicesDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.Money;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
class AppointmentServices implements SelfValidator<AppointmentServices> {

    private final List<AppointmentService> services = new ArrayList<>();

    AppointmentServices addAll(List<ServiceDto> args) {
        requireNonNull(args, "Appointment services must not be null");
        this.services.addAll(toAppointmentService(args));
        return null;
    }

    AppointmentServices add(AppointmentService arg) {
        requireNonNull(arg, "Appointment service must not be null");
        services.add(arg);
        return this;
    }

    String joiningNames(String delimiter) {
        return services.stream()
                .map(AppointmentService::name)
                .collect(joining(delimiter));
    }

    BigDecimal totalPrice() {
        return services.stream()
                .map(AppointmentService::price)
                .map(Money::amount)
                .reduce(ZERO, BigDecimal::add);
    }

    Duration totalDuration() {
        return services.stream()
                .map(AppointmentService::duration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    AppointmentServicesDto toDto() {
        return AppointmentServicesDto.builder()
                .name(joiningNames("\n"))
                .durationInMinutes(totalDuration().toMinutes())
                .price(totalPrice())
                .services(servicesToDto())
                .build();
    }

    @Override
    public AppointmentServices validate() {
        if (services.size() == 0) {
            throw new ValidationException("Number of appointment services must not be zero ");
        }
        validate(services);
        return this;
    }

    @Override
    public String toString() {
        return "AppointmentServices{" +
                "numberOfServices=" + services.size() +
                '}';
    }

    private List<AppointmentService> toAppointmentService(List<ServiceDto> args) {
        return args.stream()
                .map(AppointmentService::new)
                .collect(toList());
    }

    private List<AppointmentServiceDto> servicesToDto() {
        return services.stream()
                .map(AppointmentService::toDto)
                .collect(toList());
    }

}
