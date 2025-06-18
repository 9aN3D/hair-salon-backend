package pl.edu.wit.hairsalon.service;

import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;

import java.time.Duration;

import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.service.query.ServiceFindQuery.withId;

class ServiceUpdater {

    private final ServicePort servicePort;

    ServiceUpdater(ServicePort servicePort) {
        this.servicePort = servicePort;
    }

    Service update(String serviceId, ServiceUpdateCommand command) {
        var serviceDto = servicePort.findOneOrThrow(withId(serviceId));
        return buildService(serviceDto, command).validate();
    }

    private Service buildService(ServiceDto dto, ServiceUpdateCommand command) {
        return Service.builder()
                .id(dto.id())
                .name(getName(dto, command))
                .price(getPrice(dto, command))
                .duration(getDuration(dto, command))
                .build();
    }

    private Money getPrice(ServiceDto dto, ServiceUpdateCommand command) {
        return ofNullable(command.price())
                .map(Money::ofPln)
                .orElseGet(() -> Money.of(dto.price()));
    }

    private String getName(ServiceDto dto, ServiceUpdateCommand command) {
        return ofNullable(command.name())
                .orElseGet(dto::name);
    }

    private Duration getDuration(ServiceDto dto, ServiceUpdateCommand command) {
        return ofNullable(command.durationInMinutes())
                .map(Duration::ofMinutes)
                .orElseGet(() -> Duration.ofMinutes(dto.durationInMinutes()));
    }

}
