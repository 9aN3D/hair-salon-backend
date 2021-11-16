package pl.edu.wit.hairsalon.service;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.exception.ServiceAlreadyExists;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.time.Duration;

import static java.lang.String.format;

@RequiredArgsConstructor
class ServiceCreator {

    private final ServicePort servicePort;
    private final IdGenerator idGenerator;

    Service create(ServiceCreateCommand command) {
        throwIfExistProductName(command.getName());
        return createNewService(command).validate();
    }

    private void throwIfExistProductName(String name) {
        if (existByProductName(name)) {
            throw new ServiceAlreadyExists(
                    format("Product already exists by name: %s", name)
            );
        }
    }

    private Boolean existByProductName(String name) {
        return servicePort.findOne(ServiceFindQuery.withName(name)).isPresent();
    }

    public Service createNewService(ServiceCreateCommand command) {
        return Service.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .price(Money.ofPln(command.getPrice()))
                .duration(Duration.ofMinutes(command.getDurationInMinutes()))
                .build();
    }

}
