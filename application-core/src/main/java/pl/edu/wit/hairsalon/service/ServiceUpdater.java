package pl.edu.wit.hairsalon.service;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;

import java.time.Duration;

import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.service.query.ServiceFindQuery.withId;

@RequiredArgsConstructor
class ServiceUpdater {

    private final ServicePort servicePort;

    Service update(String serviceId, ServiceUpdateCommand command) {
        var serviceDto = servicePort.findOneOrThrow(withId(serviceId));
        return buildService(serviceDto, command).validate();
    }

    private Service buildService(ServiceDto dto, ServiceUpdateCommand command) {
        return Service.builder()
                .id(dto.getId())
                .name(getName(dto, command))
                .price(getPrice(dto, command))
                .duration(getDuration(dto, command))
                .build();
    }

    private Money getPrice(ServiceDto dto, ServiceUpdateCommand command) {
        return ofNullable(command.getPrice())
                .map(Money::ofPln)
                .orElseGet(() -> Money.of(dto.getPrice()));
    }

    private String getName(ServiceDto dto, ServiceUpdateCommand command) {
        return ofNullable(command.getName())
                .orElseGet(dto::getName);
    }

    private Duration getDuration(ServiceDto dto, ServiceUpdateCommand command) {
        return ofNullable(command.getDurationInMinutes())
                .map(Duration::ofMinutes)
                .orElseGet(() -> Duration.ofMinutes(dto.getDurationInMinutes()));
    }

}
