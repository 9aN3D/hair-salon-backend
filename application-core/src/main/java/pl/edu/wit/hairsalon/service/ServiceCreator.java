package pl.edu.wit.hairsalon.service;

import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.exception.ServiceAlreadyExists;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.time.Duration;

import static java.lang.String.format;

class ServiceCreator {

    private final ServicePort servicePort;
    private final IdGenerator idGenerator;

    ServiceCreator(ServicePort servicePort, IdGenerator idGenerator) {
        this.servicePort = servicePort;
        this.idGenerator = idGenerator;
    }

    Service create(ServiceCreateCommand command) {
        throwIfExistProductName(command.name());
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

    private Service createNewService(ServiceCreateCommand command) {
        return Service.builder()
                .id(idGenerator.generate())
                .name(command.name())
                .price(Money.ofPln(command.price()))
                .duration(Duration.ofMinutes(command.durationInMinutes()))
                .build();
    }

}
