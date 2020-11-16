package pl.edu.wit.domain.salon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.domain.IdGenerator;
import pl.edu.wit.domain.salon.port.primary.CreateSalonHandler;
import pl.edu.wit.domain.salon.port.secondary.SalonRepository;
import pl.edu.wit.domain.salon.shared.CreateSalonCommand;
import pl.edu.wit.domain.salon.shared.CreateSalonResult;

@Slf4j
@RequiredArgsConstructor
class DomainCreateSalonHandler implements CreateSalonHandler {

    private final IdGenerator idGenerator;
    private final SalonRepository salonRepository;

    @Override
    public CreateSalonResult handle(CreateSalonCommand command) {
        log.trace("Creating salon: {command: {}}", command);
        var salon = new DefaultSalon(idGenerator, command);
        salonRepository.save(salon.toDto());
        var result = salon.result();
        log.info("Created salon: {result: {}}", result);
        return result;
    }

}
